package com.jenna.pennypilot.domain.transaction.mapper;

import com.jenna.pennypilot.domain.transaction.dto.DailyTransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionParamDTO;
import com.jenna.pennypilot.domain.transaction.dto.TransactionResultDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionMapper {

    List<TransactionResultDTO> selectAllTransactionsByMonth(TransactionParamDTO params);

    List<DailyTransactionDTO> selectDailyTotalsByMonth(TransactionParamDTO params);

    TransactionResultDTO selectOneTransactionById(int id);

    void addTransaction(TransactionDTO transaction);

    void updateTransaction(TransactionDTO transaction);

    void deleteTransaction(int id);

}
