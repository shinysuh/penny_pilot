package com.jenna.pennypilot.domain.transaction.service;

import com.jenna.pennypilot.domain.transaction.dto.PeriodParamDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionResultDTO;
import com.jenna.pennypilot.domain.transaction.dto.ctg.TotalByPeriodDTO;
import com.jenna.pennypilot.domain.transaction.dto.monthly.MonthlyTransactionDTO;

public interface TransactionService {

    MonthlyTransactionDTO getMonthlyTransactions(PeriodParamDTO params);

    TotalByPeriodDTO getCtgTotalByPeriod(PeriodParamDTO params, String periodType);

    TransactionResultDTO getOneTransactionById(int id);

    TransactionDTO addTransaction(TransactionDTO transaction);

    void updateTransaction(TransactionDTO transaction);

    void deleteTransaction(int id);

}
