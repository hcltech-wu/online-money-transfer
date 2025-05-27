package com.callisto.service;

import org.springframework.stereotype.Service;

import com.callisto.model.Transaction;
import com.callisto.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getTransactionsBySenderEmailId(String senderEmailId) {
        logger.info("Getting Transaction by senderEmailId : " + senderEmailId);
        return repository.findBySenderEmailId(senderEmailId);
    }

    public Transaction createTransaction(Transaction transaction) {
        logger.info("Creating Transaction : " + transaction);
        return repository.save(transaction);
    }

}
