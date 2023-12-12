package com.project.spendwise.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.spendwise.entities.Transaction;
import com.project.spendwise.entities.TransactionType;
import com.project.spendwise.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class TransactionServiceTest {

	@Autowired
	private TransactionService transactionService;

	@MockBean
	private TransactionRepository transactionRepository;

	@Test
	public void testAddExpenseTransaction() {
		Transaction expenseTransaction = new Transaction("Compra", 50.0, TransactionType.EXPENSE);

		// Simulando o comportamento do repository
		when(transactionRepository.save(any(Transaction.class))).thenReturn(expenseTransaction);

		// Adicionando a transação de despesa
		Transaction addedTransaction = transactionService.addTransaction(expenseTransaction);

		// Verificando se a transação foi adicionada corretamente
		assertNotNull(addedTransaction);
		assertEquals("Compra", addedTransaction.getDescription());
		assertEquals(50.0, addedTransaction.getAmount());
		assertEquals(TransactionType.EXPENSE, addedTransaction.getType());

		// Verificando se o saldo total foi atualizado corretamente
		assertEquals(950.0, transactionService.getTotalBalance());
	}
	
	@Test
	public void testAddOneMoreExpenseTransaction() {
		Transaction transaction= new Transaction("Academia", 80.0, TransactionType.EXPENSE);
		
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
		
		Transaction addedTransaction = transactionService.addTransaction(transaction);
		
		assertNotNull(addedTransaction);
		assertEquals("Academia", addedTransaction.getDescription());
		assertEquals(80.00, addedTransaction.getAmount());
		assertEquals(TransactionType.EXPENSE, addedTransaction.getType());
		
		assertEquals(870.0, transactionService.getTotalBalance());
		
		
	}

	@Test
	public void testAddIncomeTransaction() {
		Transaction incomeTransaction = new Transaction("Salário", 1000.0, TransactionType.INCOME);

		when(transactionRepository.save(any(Transaction.class))).thenReturn(incomeTransaction);

		Transaction addedTransaction = transactionService.addTransaction(incomeTransaction);

		assertNotNull(addedTransaction);
		assertEquals("Salário", addedTransaction.getDescription());
		assertEquals(1000.0, addedTransaction.getAmount());
		assertEquals(TransactionType.INCOME, addedTransaction.getType());

		assertEquals(1000.0, transactionService.getTotalBalance());
	}

}
