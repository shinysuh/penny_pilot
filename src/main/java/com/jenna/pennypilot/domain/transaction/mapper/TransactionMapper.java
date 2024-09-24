package com.jenna.pennypilot.domain.transaction.mapper;

import com.jenna.pennypilot.domain.transaction.dto.PeriodParamDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionResultDTO;
import com.jenna.pennypilot.domain.transaction.dto.ctg.TotalByCtgDTO;
import com.jenna.pennypilot.domain.transaction.dto.ctg.TotalByTypeDTO;
import com.jenna.pennypilot.domain.transaction.dto.monthly.DailyTransactionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionMapper {

    List<TransactionResultDTO> selectAllTransactionsByMonth(PeriodParamDTO params);

    List<DailyTransactionDTO> selectDailyTotalsByMonth(PeriodParamDTO params);

    // TODO - 추후 연/월별 total 출력 목록에 사용
    List<TotalByTypeDTO> selectTypeTotalsByPeriod(PeriodParamDTO params);

    List<TotalByCtgDTO> selectCtgTotalsByPeriod(PeriodParamDTO params);

    TransactionResultDTO selectOneTransactionById(int id);

    void addTransaction(TransactionDTO transaction);

    void updateTransaction(TransactionDTO transaction);

    void deleteTransaction(int id);

}
