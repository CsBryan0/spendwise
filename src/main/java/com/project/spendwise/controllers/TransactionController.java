package com.project.spendwise.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.spendwise.entities.Transaction;
import com.project.spendwise.entities.TransactionType;
import com.project.spendwise.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity<Transaction> addTransaction(@RequestBody @Valid Transaction transaction) {
		Transaction createdTransaction = transactionService.addTransaction(transaction);
		return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> interceptarExcecaoValidacao(MethodArgumentNotValidException ex){
		Map<String, String> erros = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			
			erros.put(fieldName, errorMessage);
		});
		
		
		return erros;
	}

	@GetMapping
	public ResponseEntity<List<Transaction>> getAllTransaction() {
		List<Transaction> transactions = transactionService.getAllTransactions();
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable int id) {
		Optional<Transaction> transaction = transactionService.getTransactionById(id);
		if (transaction.isPresent()) {
			return new ResponseEntity<>(transaction, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{type}")
	public ResponseEntity<List<Transaction>> getTransactionByType(TransactionType type){
		List<Transaction> typeOfTransaction = transactionService.getTransactionByType(type);
		
		if(typeOfTransaction.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(typeOfTransaction, HttpStatus.OK);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Transaction> updateTask(@PathVariable Integer id,
			@RequestBody Transaction updateTransaction) {
		try {
			Optional<Transaction> existingTransaction = transactionService.getTransactionById(id);

			if (existingTransaction.isPresent()) {
				Transaction transactionToUpdate = existingTransaction.get();

				transactionToUpdate.setAmount(updateTransaction.getAmount());
				transactionToUpdate.setDescription(updateTransaction.getDescription());
				transactionToUpdate.setType(updateTransaction.getType());

				Transaction updatedTransaction = transactionService.updateTransaction(transactionToUpdate);
				return ResponseEntity.ok(updatedTransaction);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Transaction> deleteTransaction(@PathVariable int id){
		Transaction removedTransaction = transactionService.removeTransaction(id);
	
		if(removedTransaction != null) {
			return ResponseEntity.ok(removedTransaction);
		}
		
		return ResponseEntity.notFound().build();
	
	}


}
