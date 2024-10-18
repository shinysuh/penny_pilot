package com.jenna.pennypilot.domain.transaction.services;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.transaction.constants.PeriodType;
import com.jenna.pennypilot.domain.transaction.constants.TransactionType;
import com.jenna.pennypilot.domain.transaction.dtos.PeriodParamDTO;
import com.jenna.pennypilot.domain.transaction.dtos.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.dtos.TransactionResultDTO;
import com.jenna.pennypilot.domain.transaction.dtos.ctg.TotalByCtgDTO;
import com.jenna.pennypilot.domain.transaction.dtos.ctg.TotalByPeriodDTO;
import com.jenna.pennypilot.domain.transaction.dtos.ctg.TotalByTypeDTO;
import com.jenna.pennypilot.domain.transaction.dtos.monthly.DailyTransactionDTO;
import com.jenna.pennypilot.domain.transaction.dtos.monthly.MonthlyTransactionDTO;
import com.jenna.pennypilot.domain.transaction.mappers.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /* TODO - 아래 메소드 리팩토링
        && 다른 조건들도 적용 가능하게 변경 OR 메소드 생성 (day 부분 변경)
                >> 다른 조건들: 은행별 / 계좌별 / 계좌타입별 / 거래타입별
     */

    @Override
    public MonthlyTransactionDTO getMonthlyTransactions(PeriodParamDTO params) {
        // 날짜 형식 validation
        params.setPeriodType(PeriodType.MONTH.getName());
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
                .transactionMonth(params.getTransactionPeriod())
                .totalIncome(monthlyTotalIncome)
                .totalExpense(monthlyTotalExpense)
                .transactionByDays(dailyTotals)
                .build();
    }

    @Override
    public TotalByPeriodDTO getCtgTotalByPeriod(PeriodParamDTO params, String periodType) {
        // 날짜 형식 validation
        this.setPeriodType(params, periodType);
        this.validateDateFormat(params);

        List<TotalByCtgDTO> ctgTotals = transactionMapper.selectCtgTotalsByPeriod(params);

        // 수입, 지출
        String[] types = {
                TransactionType.INCOME.getType(),
                TransactionType.EXPENSE.getType(),
        };

        List<TotalByTypeDTO> totalByTypes = new ArrayList<>();
        long total;

        for (String type : types) {
            // 타입별 카테고리 total 내역
            List<TotalByCtgDTO> ctgTotalByTypes = ctgTotals.stream()
                    .filter(ctgTotal -> type.equalsIgnoreCase(ctgTotal.getTransactionType()))
                    .toList();

            // 타입별 total 집계
            total = ctgTotalByTypes.stream().mapToLong(TotalByCtgDTO::getTotalAmount).sum();

            totalByTypes.add(TotalByTypeDTO.builder()
                    .transactionType(type)
                    .totalAmount(total)
                    .ctgTotals(ctgTotalByTypes)
                    .build());
        }

        return TotalByPeriodDTO.builder()
                .transactionPeriod(params.getTransactionPeriod())
                .transactions(totalByTypes)
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
    private void validateDateFormat(PeriodParamDTO params) {
        int subStrEnd = 0;
        String datePattern = "";
        String periodFormat = "";

        PeriodType periodType = PeriodType.valueOf(params.getPeriodType().toUpperCase());

        switch (periodType) {
            case YEAR -> {
                subStrEnd = 4;
                datePattern = "^\\d{4}$";
                periodFormat = "YYYY";
            }
            case MONTH -> {
                subStrEnd = 7;
                datePattern = "^\\d{4}-(0[1-9]|1[0-2])$";
                periodFormat = "YYYY-MM";
            }
            case DAY -> {
                subStrEnd = 10;
                datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
                periodFormat = "YYYY-MM-DD";
            }
        }

        String targetPeriod = params.getTransactionPeriod().substring(0, subStrEnd);
        params.setTransactionPeriod(targetPeriod);
        params.setPeriodFormat(periodFormat);

        Matcher matcher = Pattern.compile(datePattern).matcher(targetPeriod);

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

    /**
     * periodType 설정
     */
    private void setPeriodType(PeriodParamDTO params, String periodType) {
        params.setPeriodType(
                periodType.toLowerCase().equals(PeriodType.YEAR.getName()) ? PeriodType.YEAR.getName()
                        : periodType.equals(PeriodType.DAY.getName()) ? PeriodType.DAY.getName()
                        : PeriodType.MONTH.getName()
        );
    }
}
