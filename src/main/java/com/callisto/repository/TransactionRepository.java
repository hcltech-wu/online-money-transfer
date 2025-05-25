package com.callisto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.callisto.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderEmailId(String senderEmailId);

}
