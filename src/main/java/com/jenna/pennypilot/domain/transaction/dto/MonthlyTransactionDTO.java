package com.jenna.pennypilot.domain.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTransactionDTO {

    private int userId;

    private String transactionMonth;  // yyyy-mm

    private long totalIncome;

    private long totalExpense;

    private List<DailyTransactionDTO> transactionByDays;
}
