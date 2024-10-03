package com.jenna.pennypilot.domain.transaction.dtos;

import com.jenna.pennypilot.domain.transaction.constants.PeriodType;
import lombok.Data;

@Data
public class PeriodParamDTO {

    private int userId;

    private String transactionPeriod;       // 연 / 월 / 일 기준

    private PeriodType periodType;      // year / month / day

    private String periodFormat;        // YYYY / YYYY-MM / YYYY-MM-DD
}
