package com.jenna.pennypilot.domain.transaction.dto.monthly;

import com.jenna.pennypilot.domain.transaction.dto.TransactionResultDTO;
import lombok.Data;

import java.util.List;

@Data
public class DailyTransactionDTO {

    private String day;

    private long totalIncome;

    private long totalExpense;

    private List<TransactionResultDTO> transactions;
}
