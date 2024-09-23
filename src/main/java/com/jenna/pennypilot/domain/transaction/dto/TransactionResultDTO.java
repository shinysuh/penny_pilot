package com.jenna.pennypilot.domain.transaction.dto;

import lombok.Data;

@Data
public class TransactionResultDTO {

    private int id;

    private int accountId;

    private String bankName;

    private String accountName;

    private String accountType;

    private int ctgId;

    private String ctgNm;

    private long amount;

    private String transactionType;

    private String transactionDate;

    private String description;

}
