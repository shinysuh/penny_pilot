package com.jenna.pennypilot.domain.currency.controllers;

import com.jenna.pennypilot.domain.currency.dtos.CurrencyDTO;
import com.jenna.pennypilot.domain.currency.services.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Operation(summary = "통화 정보 전체 조회", description = "order by country")
    @GetMapping("")
    public ResponseEntity<?> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @Operation(summary = "통화 정보 하나 조회", description = "code 기준")
    @GetMapping("/{code}")
    public ResponseEntity<?> getOneCurrencyByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(currencyService.getOneCurrencyByCode(code));
    }

    @Operation(summary = "통화 정보 추가")
    @PostMapping("")
    public ResponseEntity<?> addCurrency(@RequestBody CurrencyDTO currency) {
        currencyService.addCurrency(currency);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "통화 정보 삭제", description = "code / name / country 정보 모두 필요")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteCurrency(@RequestBody CurrencyDTO currency) {
        currencyService.deleteCurrency(currency);
        return ResponseEntity.ok().build();
    }

}
