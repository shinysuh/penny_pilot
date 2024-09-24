package com.jenna.pennypilot.domain.transaction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PeriodType {
    YEAR("year"),
    MONTH("month"),
    DAY("day"),

    ;

    private final String name;
}
