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

import java.util.*;
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
        Map<String, Long> monthlyTotals = this.getDailyTransactions(params, dailyTotals);

        return MonthlyTransactionDTO.builder()
                .transactionMonth(params.getTransactionPeriod())
                .totalIncome(monthlyTotals.get(TransactionType.INCOME.getType()))
                .totalExpense(monthlyTotals.get(TransactionType.EXPENSE.getType()))
                .transactionByDays(dailyTotals)
                .build();
    }

    private Map<String, Long> getMonthlyTotals() {
        Map<String, Long> monthlyTotal = new HashMap<>();
        monthlyTotal.put(TransactionType.INCOME.getType(), 0L);
        monthlyTotal.put(TransactionType.EXPENSE.getType(), 0L);
        return monthlyTotal;
    }

    private void updateMonthlyTotals(Map<String, Long> monthlyTotal, String target, long value) {
        monthlyTotal.put(target, monthlyTotal.get(target) + value);
    }

    private Map<String, Long> getDailyTransactions(PeriodParamDTO params, List<DailyTransactionDTO> dailyTotals) {
        Map<String, Long> monthlyTotals = this.getMonthlyTotals();
        // 해당 월의 전체 transaction list
        List<TransactionResultDTO> transactions = transactionMapper.selectAllTransactionsByMonth(params);

        for (DailyTransactionDTO daily : dailyTotals) {
            this.setTransactionByDay(monthlyTotals, transactions, daily);
        }
        return monthlyTotals;
    }

    private void setTransactionByDay(Map<String, Long> monthlyTotals, List<TransactionResultDTO> transactions, DailyTransactionDTO daily) {
        this.updateMonthlyTotals(monthlyTotals, TransactionType.INCOME.getType(), daily.getTotalIncome());
        this.updateMonthlyTotals(monthlyTotals, TransactionType.EXPENSE.getType(), daily.getTotalExpense());

        // day 날짜에 해당하는 transactions 필터링
        List<TransactionResultDTO> trs = transactions.stream()
                .filter(tr -> tr.getTransactionDate().contains(daily.getDay()))
                .toList();
        daily.setTransactions(trs);
    }

    @Override
    public TotalByPeriodDTO getCtgTotalByPeriod(PeriodParamDTO params, String periodType) {
        // 날짜 형식 validation
        this.setPeriodType(params, periodType);
        this.validateDateFormat(params);
        return TotalByPeriodDTO.builder()
                .transactionPeriod(params.getTransactionPeriod())
                .transactions(this.getTotalByTypes(params))
                .build();
    }

    private List<TotalByTypeDTO> getTotalByTypes(PeriodParamDTO params) {
        List<TotalByTypeDTO> totalByTypes = new ArrayList<>();
        List<TotalByCtgDTO> ctgTotals = transactionMapper.selectCtgTotalsByPeriod(params);
        for (String type : this.getTypes()) {
            totalByTypes.add(this.getTotalByType(ctgTotals, type));
        }
        return totalByTypes;
    }

    private TotalByTypeDTO getTotalByType(List<TotalByCtgDTO> ctgTotals, String type) {
        List<TotalByCtgDTO> ctgTotalByTypes = this.getCtgTotalByTypes(ctgTotals, type);
        return TotalByTypeDTO.builder()
                .transactionType(type)
                .totalAmount(this.getTotalAmountByType(ctgTotalByTypes))
                .ctgTotals(ctgTotalByTypes)
                .build();
    }

    private List<TotalByCtgDTO> getCtgTotalByTypes(List<TotalByCtgDTO> ctgTotals, String type) {
        return ctgTotals.stream()
                .filter(ctgTotal -> type.equalsIgnoreCase(ctgTotal.getTransactionType()))
                .toList();
    }

    private long getTotalAmountByType(List<TotalByCtgDTO> ctgTotalByTypes) {
        return ctgTotalByTypes.stream().mapToLong(TotalByCtgDTO::getTotalAmount).sum();
    }

    private String[] getTypes() {
        // 수입, 지출
        return new String[]{
                TransactionType.INCOME.getType(),
                TransactionType.EXPENSE.getType(),
        };
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
        PeriodType periodType;

        switch (params.getPeriodType().toLowerCase()) {
            case "year" -> periodType = PeriodType.YEAR;
            case "day" -> periodType = PeriodType.DAY;
            default -> periodType = PeriodType.MONTH;
        }

        this.setPeriodParams(params, periodType);
    }

    private void setPeriodParams(PeriodParamDTO params, PeriodType periodType) {
        String targetPeriod = params.getTransactionPeriod().substring(0, periodType.getSubStrEnd());
        params.setTransactionPeriod(targetPeriod);
        params.setPeriodFormat(periodType.getPeriodFormat());

        this.validateTransactionDate(periodType.getDatePattern(), targetPeriod);
    }

    private void validateTransactionDate(String datePattern, String targetPeriod) {
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
