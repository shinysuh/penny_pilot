package com.jenna.pennypilot.dto;

import com.jenna.pennypilot.domain.transaction.dtos.TransactionResultDTO;
import lombok.Data;

import java.util.List;

@Data
public class TestTransactionDTO {
    List<TransactionResultDTO> transactions;
}
