package com.jenna.pennypilot.domain.transaction;

import com.jenna.pennypilot.domain.transaction.dtos.PeriodParamDTO;
import com.jenna.pennypilot.domain.transaction.mappers.TransactionMapper;
import com.jenna.pennypilot.domain.transaction.services.TransactionService;
import com.jenna.pennypilot.domain.transaction.services.TransactionServiceImpl;
import com.jenna.pennypilot.dto.TestTransactionDTO;
import com.jenna.pennypilot.util.TestCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class TransactionServiceTest {

//    @Autowired
//    private MockMvc mockMvc;

    @Mock
    private TransactionMapper transactionMapper;

    private TransactionService transactionService;

    private TestTransactionDTO testTransactionDTO;

    private final String TRANSACTION_PERIOD = "2024-11";

    private final String PERIOD_FORMAT = "month";

    @BeforeEach
    public void setUp() {
        testTransactionDTO = TestCommonUtil.getJsonData("Transaction", TestTransactionDTO.class);
        transactionService = new TransactionServiceImpl(transactionMapper);

        PeriodParamDTO params = PeriodParamDTO.builder()
                .transactionPeriod(TRANSACTION_PERIOD)
                .periodFormat(PERIOD_FORMAT)
                .build();

        when(transactionMapper.selectAllTransactionsByMonth(params))
                .thenReturn(testTransactionDTO.getTransactions());

        when(transactionMapper.selectDailyTotalsByMonth(params))
                .thenReturn(testTransactionDTO.getDailyTransactions());

        when(transactionMapper.selectTypeTotalsByPeriod(params))
                .thenReturn(testTransactionDTO.getTypeTotals());

        when(transactionMapper.selectCtgTotalsByPeriod(params))
                .thenReturn(testTransactionDTO.getCtgTotals());
    }

}
