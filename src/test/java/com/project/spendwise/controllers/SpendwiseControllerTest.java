package com.project.spendwise.controllers;

import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.spendwise.entities.Transaction;
import com.project.spendwise.entities.TransactionType;
import com.project.spendwise.services.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class SpendwiseControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;
	
	@Test
	public void getAllTransaction() throws Exception{
		List<Transaction> transactions = Arrays.asList(
				new Transaction("Academia", 80.0, TransactionType.EXPENSE),
				new Transaction("Salário", 1200.0, TransactionType.INCOME)
				);
		
		when(transactionService.getAllTransactions()).thenReturn(transactions);
		
       mockMvc.perform(get("/transactions"))
       			.andExpect(status().isOk())
       			.andExpect(jsonPath("$", hasSize(2)))
       			.andReturn();
	}
	
	@Test
	public void addTransaction() throws Exception{
		Transaction transaction = new Transaction();
		
		transaction.setAmount(1500.0);
		transaction.setDescription("Salário");
		transaction.setType(TransactionType.INCOME);
		
		
		when(transactionService.addTransaction(transaction)).thenReturn(transaction);
	
		 mockMvc.perform(post("/transactions")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(transaction))) 
		            .andExpect(status().isCreated())
		            .andReturn();
	}
	
	
	
	
	 protected String asJsonString(Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}
