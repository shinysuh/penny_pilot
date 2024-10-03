package com.jenna.pennypilot.domain.transaction.dtos.ctg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalByPeriodDTO {

    private String transactionPeriod;       // 연 / 월 / 일 기준

    private List<TotalByTypeDTO> transactions;
}
