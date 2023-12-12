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

	// Remover transação
	public void removeTransaction(int id) {
		transactionRepository.deleteById(id);
	}
	
	// Listar transações 
	public List<Transaction> getAllTransactions(){
		return transactionRepository.findAll();
	}
	
	// Listar transações com ID
	public Optional<Transaction> getTransactionById(int id){
		return transactionRepository.findById(id);	
	}

	private void updateBalance(double amount, TransactionType type) {
		if (type == TransactionType.EXPENSE) {
			totalBalance -= amount;
		} else if (type == TransactionType.INCOME) {
			totalBalance += amount;
		}
	}

    public Double getTotalBalance() {
        return totalBalance;
    }
    

}
