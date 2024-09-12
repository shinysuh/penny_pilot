package com.jenna.pennypilot.domain.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {

    private int id;

    private int userId;

    private int ctgId;

    private long budgetAmount;

    private String startDate;

    private String endDate;

    private String createdAt;

    private String updatedAt;

}
