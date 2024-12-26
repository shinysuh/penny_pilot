package com.jenna.pennypilot.dto;

import com.jenna.pennypilot.domain.transaction.dtos.TransactionResultDTO;
import com.jenna.pennypilot.domain.transaction.dtos.ctg.TotalByCtgDTO;
import com.jenna.pennypilot.domain.transaction.dtos.ctg.TotalByPeriodDTO;
import com.jenna.pennypilot.domain.transaction.dtos.ctg.TotalByTypeDTO;
import com.jenna.pennypilot.domain.transaction.dtos.monthly.DailyTransactionDTO;
import lombok.Data;

import java.util.List;

@Data
public class TestTransactionDTO {
    List<TransactionResultDTO> transactions;

    List<DailyTransactionDTO> dailyTransactions;

    List<TotalByTypeDTO> typeTotals;

    TotalByPeriodDTO totalByPeriod;

    List<TotalByCtgDTO> ctgTotals;
}
