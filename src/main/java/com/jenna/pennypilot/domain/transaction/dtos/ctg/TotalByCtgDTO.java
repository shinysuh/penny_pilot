package com.jenna.pennypilot.domain.transaction.dtos.ctg;

import lombok.Data;

@Data
public class TotalByCtgDTO {

    private String transactionType;

    private int ctgId;

    private String ctgNm;

    private long totalAmount;
}
