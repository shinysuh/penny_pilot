package com.jenna.pennypilot.domain.transaction.services;

import com.jenna.pennypilot.domain.transaction.dtos.PeriodParamDTO;
import com.jenna.pennypilot.domain.transaction.dtos.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.dtos.TransactionResultDTO;
import com.jenna.pennypilot.domain.transaction.dtos.ctg.TotalByPeriodDTO;
import com.jenna.pennypilot.domain.transaction.dtos.monthly.MonthlyTransactionDTO;

public interface TransactionService {

    MonthlyTransactionDTO getMonthlyTransactions(PeriodParamDTO params);

    TotalByPeriodDTO getCtgTotalByPeriod(PeriodParamDTO params, String periodType);

    TransactionResultDTO getOneTransactionById(int id);

    TransactionDTO addTransaction(TransactionDTO transaction);

    void updateTransaction(TransactionDTO transaction);

    void deleteTransaction(int id);

}
