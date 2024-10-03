package com.jenna.pennypilot.domain.transaction.dtos.monthly;

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

    private String transactionMonth;  // yyyy-mm

    private long totalIncome;

    private long totalExpense;

    private List<DailyTransactionDTO> transactionByDays;
}
