package com.jenna.pennypilot.domain.transaction.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodParamDTO {

    private int userId;

    private String transactionPeriod;       // 연 / 월 / 일 기준

    private String periodType;      // year / month / day

    private String periodFormat;        // YYYY / YYYY-MM / YYYY-MM-DD
}
