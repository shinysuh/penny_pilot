package com.jenna.pennypilot.domain.transaction.service;

import com.jenna.pennypilot.domain.transaction.dto.MonthlyTransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionParamDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionResultDTO;

public interface TransactionService {

    MonthlyTransactionDTO getMonthlyTransactions(TransactionParamDTO params);

    TransactionResultDTO getOneTransactionById(int id);

    TransactionDTO addTransaction(TransactionDTO transaction);

    void updateTransaction(TransactionDTO transaction);

    void deleteTransaction(int id);

}
