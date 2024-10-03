package com.jenna.pennypilot.domain.transaction.dtos;

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

    private String transactionType;     // 수입 / 지출 / 이체

    private String transactionDate;

    private String description;

    private String createdAt;

    private String updatedAt;

}
