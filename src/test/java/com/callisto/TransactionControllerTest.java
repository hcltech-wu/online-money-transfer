package com.callisto;

import com.callisto.controller.TransactionController;
import com.callisto.model.Transaction;
import com.callisto.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction sampleTransaction;

    @BeforeEach
    void setUp() {
        sampleTransaction = new Transaction();
        sampleTransaction.setTransactionId(11L);
        sampleTransaction.setFromCountry("USA");
        sampleTransaction.setToCountry("India");
        sampleTransaction.setSenderAmount(100.00);
        sampleTransaction.setReceiverAmount(8500.00);
        sampleTransaction.setExchangeRate(85.00);
        sampleTransaction.setFee(1.1);
        sampleTransaction.setTotalAmount(8500.00);
        sampleTransaction.setSenderPaymentType("DC");
        sampleTransaction.setSenderCardNumber(1234567890123456L);
        sampleTransaction.setSenderCardCvv(123);
        sampleTransaction.setSenderCardType("Visa");
        sampleTransaction.setSenderEmailId("sender@example.com");
        sampleTransaction.setSenderMobileNumber(9876543210L);
        sampleTransaction.setReceiverPaymentType("CASH");
        sampleTransaction.setReceiverFirstName("John");
        sampleTransaction.setReceiverLastName("Doe");
        sampleTransaction.setReceiverMobileNumber(1234567890L);
        sampleTransaction.setTransactionDate(new Date());
        sampleTransaction.setMessage("Test Message");
        sampleTransaction.setStatus("Available");
    }

    @Test
    void testCreateTransaction() throws Exception {
        Mockito.when(transactionService.createTransaction(any(Transaction.class))).thenReturn(sampleTransaction);

        mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTransaction))).andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(11));
    }

    private Transaction getValidTransaction() {
        Transaction tx = new Transaction();
        tx.setFromCountry("USA");
        tx.setToCountry("India");
        tx.setSenderAmount(100.0);
        tx.setReceiverAmount(7500.0);
        tx.setExchangeRate(75.0);
        tx.setFee(5.0);
        tx.setTotalAmount(105.0);
        tx.setSenderPaymentType("Card");
        tx.setSenderCardNumber(1234567890123456L);
        tx.setSenderCardCvv(123);
        tx.setSenderCardType("Visa");
        tx.setSenderEmailId("sender@example.com");
        tx.setSenderMobileNumber(9876543210L);
        tx.setReceiverPaymentType("Bank");
        tx.setReceiverFirstName("John");
        tx.setReceiverLastName("Doe");
        tx.setReceiverMobileNumber(9123456789L);
        return tx;
    }

    private void performInvalidFieldTest(Transaction tx, String expectedMessage) throws Exception {
        mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tx))).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    void testMissingFromCountry() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setFromCountry(null);
        performInvalidFieldTest(tx, "From Country is required");
    }

    @Test
    void testMissingToCountry() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setToCountry(null);
        performInvalidFieldTest(tx, "To Country is required");
    }

    @Test
    void testMissingSenderAmount() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setSenderAmount(null);
        performInvalidFieldTest(tx, "Sender Amount is required");
    }

    @Test
    void testMissingReceiverAmount() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setReceiverAmount(null);
        performInvalidFieldTest(tx, "Receiver Amount is required");
    }

    @Test
    void testMissingExchangeRate() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setExchangeRate(null);
        performInvalidFieldTest(tx, "Exchange Rate is required");
    }

    @Test
    void testMissingFee() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setFee(null);
        performInvalidFieldTest(tx, "Fee is required");
    }

    @Test
    void testMissingTotalAmount() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setTotalAmount(null);
        performInvalidFieldTest(tx, "Total Amount is required");
    }

    @Test
    void testMissingSenderPaymentType() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setSenderPaymentType(null);
        performInvalidFieldTest(tx, "Sender Payment Type is required");
    }

    @Test
    void testMissingSenderCardNumber() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setSenderCardNumber(null);
        performInvalidFieldTest(tx, "Sender Card Number is required");
    }

    @Test
    void testMissingSenderCardCvv() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setSenderCardCvv(null);
        performInvalidFieldTest(tx, "Sender Card Cvv is required");
    }

    @Test
    void testMissingSenderCardType() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setSenderCardType(null);
        performInvalidFieldTest(tx, "Sender Card Type is required");
    }

    @Test
    void testMissingSenderEmailId() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setSenderEmailId(null);
        performInvalidFieldTest(tx, "Sender Email Id is required");
    }

    @Test
    void testMissingSenderMobileNumber() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setSenderMobileNumber(null);
        performInvalidFieldTest(tx, "Sender Mobile Number is required");
    }

    @Test
    void testMissingReceiverPaymentType() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setReceiverPaymentType(null);
        performInvalidFieldTest(tx, "Receiver Payment Type is required");
    }

    @Test
    void testMissingReceiverFirstName() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setReceiverFirstName(null);
        performInvalidFieldTest(tx, "Receiver First Name is required");
    }

    @Test
    void testMissingReceiverLastName() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setReceiverLastName(null);
        performInvalidFieldTest(tx, "Receiver Last Name is required");
    }

    @Test
    void testMissingReceiverMobileNumber() throws Exception {
        Transaction tx = getValidTransaction();
        tx.setReceiverMobileNumber(null);
        performInvalidFieldTest(tx, "Receiver Mobile Number is required");
    }

    @Test
    public void testGetTransactionsBySenderEmailId() throws Exception {
        String email = "test@example.com";
        Transaction transaction = new Transaction();
        transaction.setSenderEmailId(email);

        when(transactionService.getTransactionsBySenderEmailId(email))
                .thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/api/transactions").param("email", email)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].senderEmailId").value(email));
    }

}
