package com.jenna.pennypilot.domain.transaction.dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyTransactionDTO {

    private int userId;

    private String date;  // yyyy-mm

    private long totalIncome;

    private long totalExpense;

    private List<DailyTransactionDTO> transactionByDays;
}
