package com.jenna.pennypilot.domain.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private int id;

    private int accountId;

    private int ctgId;

    private long amount;

    private String transactionType;

    private String transactionDate;

    private String description;

    private String createdAt;

    private String updatedAt;

}
