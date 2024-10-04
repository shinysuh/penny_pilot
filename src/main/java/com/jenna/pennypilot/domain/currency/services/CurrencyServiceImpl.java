package com.jenna.pennypilot.domain.currency.services;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.currency.dtos.CurrencyDTO;
import com.jenna.pennypilot.domain.currency.mappers.CurrencyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.jenna.pennypilot.core.exception.ErrorCode.CURRENCY_ALREADY_EXISTS;
import static com.jenna.pennypilot.core.exception.ErrorCode.CURRENCY_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyMapper currencyMapper;


    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        return Optional.ofNullable(currencyMapper.selectAllCurrencies())
                .orElseGet(ArrayList::new);
    }

    @Override
    public CurrencyDTO getOneCurrencyByCode(String code) {
        return Optional.ofNullable(currencyMapper.selectOneCurrencyByCode(code))
                .orElseThrow(() -> new GlobalException(CURRENCY_NOT_EXISTS));
    }

    @Override
    public void addCurrency(CurrencyDTO currency) {
        // 통화 코드 중복 체크
        this.validateCurrency(currency.getCode());

        // 통화 추가
        currencyMapper.addCurrency(currency);
    }

    @Override
    public void deleteCurrency(CurrencyDTO currency) {
        currencyMapper.deleteCurrency(currency);
    }

    private void validateCurrency(String code) {
        CurrencyDTO currency = currencyMapper.selectOneCurrencyByCode(code);
        if (Objects.nonNull(currency)) throw new GlobalException(CURRENCY_ALREADY_EXISTS);
    }
}
