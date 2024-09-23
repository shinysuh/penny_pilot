package com.jenna.pennypilot.domain.transaction.service;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.transaction.constant.TransactionType;
import com.jenna.pennypilot.domain.transaction.dto.*;
import com.jenna.pennypilot.domain.transaction.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jenna.pennypilot.core.exception.ErrorCode.INVALID_TRANSACTION_DATE;
import static com.jenna.pennypilot.core.exception.ErrorCode.TRANSACTION_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;


    @Override
    public MonthlyTransactionDTO getMonthlyTransactions(TransactionParamDTO params) {
        // 날짜 형식 validation
        this.validateDateFormat(params);

        // 날짜 / 총수입 / 총지출 정보 list
        List<DailyTransactionDTO> dailyTotals = transactionMapper.selectDailyTotalsByMonth(params);
        // 해당 월의 전체 transaction list
        List<TransactionResultDTO> transactions = transactionMapper.selectAllTransactionsByMonth(params);

        long monthlyTotalIncome = 0L;
        long monthlyTotalExpense = 0L;

        for (DailyTransactionDTO daily : dailyTotals) {
            String day = daily.getDay();

            monthlyTotalIncome += daily.getTotalIncome();
            monthlyTotalExpense += daily.getTotalExpense();

            // day 날짜에 해당하는 transactions 필터링
            List<TransactionResultDTO> trs = transactions.stream()
                    .filter(tr -> tr.getTransactionDate().contains(day))
                    .toList();

            daily.setTransactions(trs);
        }

        return MonthlyTransactionDTO.builder()
                .userId(params.getUserId())
                .transactionMonth(params.getTransactionMonth())
                .totalIncome(monthlyTotalIncome)
                .totalExpense(monthlyTotalExpense)
                .transactionByDays(dailyTotals)
                .build();
    }

    @Override
    public TransactionResultDTO getOneTransactionById(int id) {
        return Optional.ofNullable(transactionMapper.selectOneTransactionById(id))
                .orElseThrow(() -> new GlobalException(TRANSACTION_NOT_EXISTS));
    }

    @Override
    public TransactionDTO addTransaction(TransactionDTO transaction) {
        this.setTransactionType(transaction);
        transactionMapper.addTransaction(transaction);
        return transaction;
    }

    @Override
    public void updateTransaction(TransactionDTO transaction) {
        this.setTransactionType(transaction);
        transactionMapper.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(int id) {
        transactionMapper.deleteTransaction(id);
    }

    /**
     * 거래 날짜 validation
     */
    private void validateDateFormat(TransactionParamDTO params) {
        String targetMonth = params.getTransactionMonth().substring(0, 7);
        params.setTransactionMonth(targetMonth);

        String datePattern = "^(\\d{4})-(0[1-9]|1[0-2])$";      // yyyy-mm
        Matcher matcher = Pattern.compile(datePattern).matcher(targetMonth);

        if (!matcher.matches()) throw new GlobalException(INVALID_TRANSACTION_DATE);
    }

    /**
     * transactionType 확실히 설정
     */
    private void setTransactionType(TransactionDTO transaction) {
        switch (transaction.getTransactionType().toLowerCase()) {
            case "income", "수입" -> transaction.setTransactionType(TransactionType.INCOME.getType());
            case "transfer", "이체" -> transaction.setTransactionType(TransactionType.TRANSFER.getType());
            default -> transaction.setTransactionType(TransactionType.EXPENSE.getType());
        }
    }
}
