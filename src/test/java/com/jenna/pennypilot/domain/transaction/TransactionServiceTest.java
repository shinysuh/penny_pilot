package com.jenna.pennypilot.domain.transaction;

import com.jenna.pennypilot.domain.transaction.mappers.TransactionMapper;
import com.jenna.pennypilot.domain.transaction.services.TransactionService;
import com.jenna.pennypilot.dto.TestTransactionDTO;
import com.jenna.pennypilot.util.TestCommonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

public class TransactionServiceTest {

//    @Autowired
//    private MockMvc mockMvc;

    @Mock
    private TransactionMapper transactionMapper;

    private TransactionService transactionService;

    private TestTransactionDTO testTransactionDTO;


    @BeforeEach
    public void setUp() {
        testTransactionDTO = TestCommonUtil.getJsonData("Transaction", TestTransactionDTO.class);
    }

}
