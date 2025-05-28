package com.callisto.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import com.callisto.model.Transaction;
import java.util.Arrays;

@Data
public class TransactionDTO {

    private Long transactionId;
    private String fromCountry;
    private String toCountry;
    private Double senderAmount;
    private Double receiverAmount;
    private Double exchangeRate;
    private Double fee;
    private Double totalAmount;
    private String senderPaymentType;
    private String senderCardNumber;
    private String senderCardType;
    private String senderEmailId;
    private Long senderMobileNumber;
    private String receiverPaymentType;
    private String receiverFirstName;
    private String receiverLastName;
    private Long receiverMobileNumber;
    private Date transactionDate;
    private String message;
    private String status;

    // Constructor to map from entity
    public TransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.fromCountry = transaction.getFromCountry();
        this.toCountry = transaction.getToCountry();
        this.senderAmount = transaction.getSenderAmount();
        this.receiverAmount = transaction.getReceiverAmount();
        this.exchangeRate = transaction.getExchangeRate();
        this.fee = transaction.getFee();
        this.totalAmount = transaction.getTotalAmount();
        this.senderPaymentType = transaction.getSenderPaymentType();
        this.senderCardNumber = maskDebitCard(transaction.getSenderCardNumber());
        this.senderCardType = transaction.getSenderCardType();
        this.senderEmailId = transaction.getSenderEmailId();
        this.senderMobileNumber = transaction.getSenderMobileNumber();
        this.receiverPaymentType = transaction.getReceiverPaymentType();
        this.receiverFirstName = transaction.getReceiverFirstName();
        this.receiverLastName = transaction.getReceiverLastName();
        this.receiverMobileNumber = transaction.getReceiverMobileNumber();
        this.transactionDate = transaction.getTransactionDate();
        this.message = transaction.getMessage();
        this.status = transaction.getStatus();
    }

    public String maskDebitCard(Long card) {
        String cardNumber = String.valueOf(card);

        int cardNumberLength = cardNumber.length();
        if (cardNumberLength <= 4) {
            return cardNumber;
        }

        char[] maskedCard = new char[cardNumberLength];
        Arrays.fill(maskedCard, 0, cardNumberLength - 4, 'X');
        cardNumber.getChars(cardNumberLength - 4, cardNumberLength, maskedCard, cardNumberLength - 4);

        return new String(maskedCard);
    }

}
