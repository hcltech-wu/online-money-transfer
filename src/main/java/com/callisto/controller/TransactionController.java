package com.callisto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.callisto.exception.TransactionNotFoundException;
import com.callisto.model.Transaction;
import com.callisto.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
        logger.info("Creating Transaction : " + transaction);
        return service.createTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> getTransactionsBySenderEmailId(@RequestParam String email) {
        logger.info("Getting Transaction by senderEmailId : " + email);
        List<Transaction> list = service.getTransactionsBySenderEmailId(email);
        if (list != null && list.size() == 0) {
            throw new TransactionNotFoundException("Transaction Not Found");
        }
        return list;
    }

}
