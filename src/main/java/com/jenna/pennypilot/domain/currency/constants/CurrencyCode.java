package com.jenna.pennypilot.domain.currency.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyCode {

    KRW("KRW", "한국", "원"),
    USD("USD", "미국", "달러"),
    JPY("JPY", "일본", "엔"),
    CAD("CAD", "캐나다", "달러"),
    AUD("AUD", "호주", "달러"),
    NZD("NZD", "뉴질랜드", "달러"),
    GBP("GBP", "영국", "파운드 스털링"),
    EUR("EUR", "유럽", "유로"),
    TWD("TWD", "대만", "달러"),
    VND("VND", "베트남", "동"),
    THB("THB", "태국", "바트"),
    TRY("TRY", "튀르키예", "리라"),
    ZWG("ZWG", "짐바브웨", "골드"),
    BRL("BRL", "브라질", "헤알"),
    MXN("MXN", "멕시코", "페소"),

    ;

    private final String code;
    private final String name;
    private final String nation;
}
