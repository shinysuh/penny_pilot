package com.jenna.pennypilot.domain.transaction.dto.ctg;

import lombok.Data;

@Data
public class TotalByCtgDTO {

    private String transactionType;

    private int ctgId;

    private String ctgNm;

    private long totalAmount;
}
