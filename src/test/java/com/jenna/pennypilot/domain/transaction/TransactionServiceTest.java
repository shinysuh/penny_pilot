package com.jenna.pennypilot.domain.transaction;

import com.jenna.pennypilot.domain.transaction.dtos.PeriodParamDTO;
import com.jenna.pennypilot.domain.transaction.mappers.TransactionMapper;
import com.jenna.pennypilot.domain.transaction.services.TransactionService;
import com.jenna.pennypilot.domain.transaction.services.TransactionServiceImpl;
import com.jenna.pennypilot.dto.TestTransactionDTO;
import com.jenna.pennypilot.util.TestCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("getMonthlyTransactions() :: 월별 거래 내역 조회")
    public void getMonthlyTransactionsTest() {
        /* TODO
             - 정상 시나리오: 월간 거래 데이터가 올바르게 집계되는지 확인.
             - 날짜 형식 검증: 잘못된 날짜 형식이 주어졌을 때 `GlobalException`이 발생하는지 확인.
             - 데이터 필터링: 일별 총수입과 총지출이 올바르게 계산되고 일별 거래 리스트가 정확히 매핑되는지 확인.
         */

        // Mock
        when(transactionMapper.selectDailyTotalsByMonth(any(PeriodParamDTO.class)))
                .thenReturn(testTransactionDTO.getDailyTransactions());

        // Test
        MonthlyTransactionDTO result = transactionService.getMonthlyTransactions(params);

        verify(transactionMapper).selectDailyTotalsByMonth(any(PeriodParamDTO.class));

        assertNotNull(result, "결과가 null이 아닙니다.");
        assertEquals(testTransactionDTO.getDailyTransactions(), result.getTransactionByDays(), "일별 총수입/총지출 데이터가 정확합니다.");
    }

    @Test
    @DisplayName("getCtgTotalByPeriod() :: 지정 기간의 카테고리별 집계 조회")
    public void getCtgTotalByPeriodTest() {
        /* TODO
             - 정상 시나리오: 주어진 기간별로 거래 데이터를 올바르게 집계하는지 확인.
             - 날짜 형식 검증: 유효하지 않은 날짜가 주어졌을 때 예외가 발생하는지 확인.
             - 거래 타입별 집계: 수입과 지출 데이터가 타입별로 올바르게 집계되는지 확인.
         */

        // Mock
        when(transactionMapper.selectCtgTotalsByPeriod(any(PeriodParamDTO.class)))
                .thenReturn(testTransactionDTO.getCtgTotals());

        // Test
        TotalByPeriodDTO result = transactionService.getCtgTotalByPeriod(params, PERIOD_TYPE);

        assertNotNull(result, "결과가 null이 아닙니다.");
        assertEquals(testTransactionDTO.getTypeTotals(), result.getTransactions(), "타입별 데이터가 정확합니다.");
//        assertIterableEquals(testTransactionDTO.getTypeTotals(), result.getTransactions(), "타입별 데이터 내용이 정확합니다.");

    }

    @Test
    @DisplayName("getOneTransactionById() :: id 기준 거래 내역 상세 조회")
    public void getOneTransactionByIdTest() {
        /* TODO
             - 정상 시나리오: 거래 ID로 단일 거래를 올바르게 조회하는지 확인.
             - 예외 처리: 거래 ID가 존재하지 않을 때 `GlobalException`이 발생하는지 확인.
         */

        int id = 3;
        TransactionResultDTO expect = testTransactionDTO.getTransactions().stream()
                .filter(tra -> tra.getId() == id)
                .findFirst()
                .orElse(testTransactionDTO.getTransactions().getFirst());

        // Mock
        when(transactionMapper.selectOneTransactionById(any(Integer.class)))
                .thenReturn(expect);

        // Test
        TransactionResultDTO actual = transactionService.getOneTransactionById(id);

        assertNotNull(actual, "결과가 null이 아닙니다.");
        assertEquals(expect, actual);
    }

    @Test
    @DisplayName("addTransaction() :: 거래 내역 추가")
    public void addTransactionTest() {
        /* TODO
             - 정상 시나리오: 거래가 정상적으로 추가되는지 확인.
             - 거래 타입 설정: 수입, 지출, 이체 등 각 거래 타입이 올바르게 설정되는지 확인.
         */

    }

    @Test
    @DisplayName("updateTransaction() :: 거래 내역 업데이트")
    public void updateTransactionTest() {
        /* TODO
             - 정상 시나리오: 거래가 정상적으로 업데이트되는지 확인.
             - 거래 타입 변경: 거래 타입이 올바르게 변경되는지 확인.
         */

    }

    @Test
    @DisplayName("deleteTransaction() :: 거래 내역 삭제")
    public void deleteTransactionTest() {
        /* TODO
             - 정상 시나리오: 거래가 정상적으로 삭제되는지 확인.
             - 예외 처리: 삭제하려는 거래가 존재하지 않을 때 예외가 발생하지는 않는지 확인.
         */

    }

    /* ********* Private 메서드에 대한 테스트 : Private 메서드는 일반적으로 직접적으로 테스트하지 않고, 이들을 호출하는 public 메서드를 통해 테스트 ********* */

    @Test
    @DisplayName("validateDateFormat() :: 날짜 형식 validation")
    public void validateDateFormatTest() {
        /* TODO
              - 날짜 형식이 유효하지 않을 때 `GlobalException`이 발생하는지 확인.
              - 날짜가 올바른 포맷으로 변경되는지 (`YYYY`, `YYYY-MM`, `YYYY-MM-DD`).
         */

    }

    @Test
    @DisplayName("setTransactionType() :: transactionType 확실히 설정")
    public void setTransactionTypeTest() {
        /* TODO
              - 거래 타입이 올바르게 설정되는지 (수입, 지출, 이체 등).
         */

    }

}
