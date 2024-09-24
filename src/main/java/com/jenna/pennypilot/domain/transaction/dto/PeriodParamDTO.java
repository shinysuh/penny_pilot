package com.jenna.pennypilot.domain.transaction.dto;

import com.jenna.pennypilot.domain.transaction.constant.PeriodType;
import lombok.Data;

@Data
public class PeriodParamDTO {

    private int userId;

    private String transactionPeriod;       // 연 / 월 / 일 기준

    private PeriodType periodType;      // year / month / day

    private String periodFormat;        // YYYY / YYYY-MM / YYYY-MM-DD
}
