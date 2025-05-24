package com.callisto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.callisto.model.Transaction;
import com.callisto.repository.TransactionRepository;
import com.callisto.service.TransactionService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService service;

    @MockBean
    private TransactionRepository repository;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setFromCountry("USA");
        transaction.setToCountry("India");
        transaction.setSenderAmount(100.0);
        transaction.setReceiverAmount(7500.0);
        transaction.setExchangeRate(75.0);
        transaction.setFee(5.0);
        transaction.setTotalAmount(105.0);
        transaction.setSenderCardNumber(1234567890123456L);
        transaction.setSenderCardCvv(123);
        transaction.setSenderCardType("Visa");
        transaction.setSenderEmailId("sender@example.com");
        transaction.setSenderMobileNumber(9876543210L);
        transaction.setReceiverPaymentType("Bank");
        transaction.setReceiverFirstName("John");
        transaction.setReceiverLastName("Doe");
        transaction.setReceiverMobileNumber(9123456789L);
    }

    @Test
    public void testCreateTransaction() {
        when(repository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction saved = service.createTransaction(transaction);

        assertThat(saved).isNotNull();
        assertThat(saved.getFromCountry()).isEqualTo("USA");
        verify(repository, times(1)).save(transaction);
    }

    @Test
    public void testGetTransactionsBySenderEmailId() {
        String email = "test@example.com";
        Transaction transaction = new Transaction();
        transaction.setSenderEmailId(email);

        when(repository.findBySenderEmailId(email)).thenReturn(Arrays.asList(transaction));

        List<Transaction> result = service.getTransactionsBySenderEmailId(email);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(email, result.get(0).getSenderEmailId());
        verify(repository, times(1)).findBySenderEmailId(email);
    }

}
