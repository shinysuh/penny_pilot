package com.jenna.pennypilot.domain.transaction.dto;

import lombok.Data;

import java.util.List;

@Data
public class DailyTransactionDTO {

    private long totalIncome;

    private long totalExpense;

    private List<TransactionDTO> transactions;
}
