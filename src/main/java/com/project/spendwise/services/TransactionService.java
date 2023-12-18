package com.project.spendwise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.spendwise.entities.Transaction;
import com.project.spendwise.entities.TransactionType;
import com.project.spendwise.repositories.TransactionRepository;

@Service
public class TransactionService {
	private Double totalBalance = 0.0;
	private final TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	// Adicionar transação
	public Transaction addTransaction(Transaction transaction) {

		if (transaction.getType() == TransactionType.EXPENSE) {
			updateBalance(transaction.getAmount(), transaction.getType());

		} else if (transaction.getType() == TransactionType.INCOME) {
			updateBalance(transaction.getAmount(), transaction.getType());
		}

		return transactionRepository.save(transaction);
	}

	// Listar transações
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	// Listar transações com ID
	public Optional<Transaction> getTransactionById(int id) {
		return transactionRepository.findById(id);
	}
	
	// Listar transações pelo tipo
	public List<Transaction> getTransactionByType(TransactionType type){
		return transactionRepository.findByType(type);
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	// Atualizar valor total
	private void updateBalance(double amount, TransactionType type) {
		if (type == TransactionType.EXPENSE) {
			totalBalance -= amount;
		} else if (type == TransactionType.INCOME) {
			totalBalance += amount;
		}
	}

	public Transaction updateTransaction(Transaction transaction) {
		Optional<Transaction> existingTransaction = transactionRepository.findById(transaction.getId());

		if (existingTransaction.isPresent()) {
			Transaction updateTransaction = existingTransaction.get();

			updateTransaction.setAmount(transaction.getAmount());
			updateTransaction.setDescription(transaction.getDescription());
			updateTransaction.setType(transaction.getType());

			updateBalance(updateTransaction.getAmount(), updateTransaction.getType());

			return transactionRepository.save(updateTransaction);

		} else {
			return null;
		}
	}
	
	
	public Transaction removeTransaction(int id) {
		Optional<Transaction> transactionToRemove = transactionRepository .findById(id);
		
		if(transactionToRemove.isPresent()) {
			Transaction removedTransaction = transactionToRemove.get();
			
			//updateBalanceOnDeletion(removedTransaction);

			transactionRepository.deleteById(removedTransaction.getId());
			
			return removedTransaction;
		}
		
		return null;
	}
	
	/*
	 * private void updateBalanceOnDeletion(Transaction removeTransaction) { double
	 * amount = removeTransaction.getAmount(); TransactionType type =
	 * removeTransaction.getType();
	 * 
	 * if(type == TransactionType.EXPENSE) { totalBalance -= amount; } else if (type
	 * == TransactionType.INCOME) { totalBalance += amount; }
	 * 
	 * }
	 */

}
