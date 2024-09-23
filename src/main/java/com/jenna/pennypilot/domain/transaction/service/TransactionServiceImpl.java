package com.jenna.pennypilot.domain.transaction.service;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.transaction.dto.MonthlyTransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionParamDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionResultDTO;
import com.jenna.pennypilot.domain.transaction.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.jenna.pennypilot.core.exception.ErrorCode.TRANSACTION_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;


    @Override
    public MonthlyTransactionDTO getMonthlyTransactions(TransactionParamDTO params) {


        // TODO - 아래 두 개 사용해서 포맷 다듬고 리턴하기 (한 달 거래 목록)
//        transactionMapper.selectAllTransactionsByMonth()
//        transactionMapper.selectDailyTotalsByMonth()


        return null;
    }

    @Override
    public TransactionResultDTO getOneTransactionById(int id) {
        return Optional.ofNullable(transactionMapper.selectOneTransactionById(id))
                .orElseThrow(() -> new GlobalException(TRANSACTION_NOT_EXISTS));
    }

    @Override
    public TransactionDTO addTransaction(TransactionDTO transaction) {
        transactionMapper.addTransaction(transaction);
        return transaction;
    }

    @Override
    public void updateTransaction(TransactionDTO transaction) {
        transactionMapper.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(int id) {
        transactionMapper.deleteTransaction(id);
    }
}
