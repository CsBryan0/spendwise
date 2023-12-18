package com.project.spendwise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.spendwise.entities.Transaction;
import com.project.spendwise.entities.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByType(TransactionType type);
}
