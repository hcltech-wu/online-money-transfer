package com.callisto.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "TRANSACTIONS_OMT")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "TRANSACTION_SEQ", allocationSize = 1)
    @Column(name = "transaction_idn_num")
    private Long transactionId;

    @NotBlank(message = "From Country is required")
    @Column(name = "from_country")
    private String fromCountry;

    @NotBlank(message = "To Country is required")
    @Column(name = "to_country")
    private String toCountry;

    @NotNull(message = "Sender Amount is required")
    @Column(name = "sender_amount")
    private Double senderAmount;

    @NotNull(message = "Receiver Amount is required")
    @Column(name = "receiver_amount")
    private Double receiverAmount;

    @NotNull(message = "Exchange Rate is required")
    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @NotNull(message = "Fee is required")
    @Column(name = "fee")
    private Double fee;

    @NotNull(message = "Total Amount is required")
    @Column(name = "total_amount")
    private Double totalAmount;

    @NotBlank(message = "Sender Payment Type is required")
    @Column(name = "sender_payment_type")
    private String senderPaymentType;

    @NotNull(message = "Sender Card Number is required")
    @Column(name = "sender_card_number")
    private Long senderCardNumber;

    @NotNull(message = "Sender Card Cvv is required")
    @Column(name = "sender_card_cvv")
    private Integer senderCardCvv;

    @NotBlank(message = "Sender Card Type is required")
    @Column(name = "sender_card_type")
    private String senderCardType;

    @NotBlank(message = "Sender Email Id is required")
    @Column(name = "sender_email_id")
    private String senderEmailId;

    @NotNull(message = "Sender Mobile Number is required")
    @Column(name = "sender_mobile_number")
    private Long senderMobileNumber;

    @NotBlank(message = "Receiver Payment Type is required")
    @Column(name = "receiver_payment_type")
    private String receiverPaymentType;

    @NotBlank(message = "Receiver First Name is required")
    @Column(name = "receiver_first_name")
    private String receiverFirstName;

    @NotBlank(message = "Receiver Last Name is required")
    @Column(name = "receiver_last_name")
    private String receiverLastName;

    @NotNull(message = "Receiver Mobile Number is required")
    @Column(name = "receiver_mobile_number")
    private Long receiverMobileNumber;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate = new Date();

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private String status = "Available";

}
