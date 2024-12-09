package com.jenna.pennypilot.domain.transaction.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PeriodType {
    YEAR("year", 4, "^\\d{4}$", "YYYY"),
    MONTH("month", 7, "^\\d{4}-(0[1-9]|1[0-2])$", "YYYY-MM"),
    DAY("day", 10, "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", "YYYY-MM-DD"),

    ;

    private final String name;
    private final int subStrEnd;
    private final String datePattern;
    private final String periodFormat;
}
