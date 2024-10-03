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
public class TotalByTypeDTO {

    private String transactionType;

    private long totalAmount;

    List<TotalByCtgDTO> ctgTotals;
}
