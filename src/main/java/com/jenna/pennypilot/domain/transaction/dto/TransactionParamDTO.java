package com.jenna.pennypilot.domain.transaction.dto;

import lombok.Data;

@Data
public class TransactionParamDTO {

    private int userId;

    private String transactionMonth;       // yyyy-mm 형태

}
