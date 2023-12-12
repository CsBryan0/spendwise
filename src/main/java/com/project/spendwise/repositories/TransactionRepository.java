package com.project.spendwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.spendwise.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
