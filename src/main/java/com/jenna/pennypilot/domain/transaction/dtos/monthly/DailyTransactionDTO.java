package com.jenna.pennypilot.domain.transaction.dtos.monthly;

import com.jenna.pennypilot.domain.transaction.dtos.TransactionResultDTO;
import lombok.Data;

import java.util.List;

@Data
public class DailyTransactionDTO {

    private String day;

    private long totalIncome;

    private long totalExpense;

    private List<TransactionResultDTO> transactions;
}
