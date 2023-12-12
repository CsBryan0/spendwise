package com.project.spendwise.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.spendwise.entities.Transaction;
import com.project.spendwise.services.TransactionService;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

	private TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@PostMapping
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction){
		Transaction createdTransaction = transactionService.addTransaction(transaction);
		return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Transaction>> getAllTransaction(){
		List<Transaction> transactions = transactionService.getAllTransactions();
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable int id){
		Optional<Transaction> transaction = transactionService.getTransactionById(id);
		if(transaction.isPresent()) {
			return new ResponseEntity<>(transaction, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}
	
	
	@GetMapping("/balance")
	public ResponseEntity<Double> getTotalBalance() {
	    Double balance = transactionService.getTotalBalance();
	    return new ResponseEntity<>(balance, HttpStatus.OK);
	}

	
	
	
}
