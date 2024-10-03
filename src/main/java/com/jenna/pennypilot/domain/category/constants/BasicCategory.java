package com.jenna.pennypilot.domain.category.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BasicCategory {

    FOOD("식비", 1),
    HOUSEHOLD("생활용품", 2),
    HEALTH("건강", 3),
    CULTURAL("문화생활", 4),
    HOUSING_AND_COMMUNICATION("주거/통신", 5),
    CELEBRATES_AND_MOURNS("경조사", 6),
    GIFT("선물", 7),
    MEMBERSHIP_AND_SUBSCRIPTION("회비", 8),
    ETC("기타", 9),
    ;

    private final String name;
    private final int seq;
}
