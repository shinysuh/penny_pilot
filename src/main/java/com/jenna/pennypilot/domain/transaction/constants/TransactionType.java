package com.jenna.pennypilot.domain.transaction.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    INCOME("income", "수입"),
    EXPENSE("expense", "지출"),
    TRANSFER("transfer", "이체"),
    ;

    private final String type;
    private final String description;
}
