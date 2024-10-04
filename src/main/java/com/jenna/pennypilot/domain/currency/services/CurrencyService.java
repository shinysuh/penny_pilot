package com.jenna.pennypilot.domain.currency.services;

import com.jenna.pennypilot.domain.currency.dtos.CurrencyDTO;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDTO> getAllCurrencies();

    CurrencyDTO getOneCurrencyByCode(String code);

    void addCurrency(CurrencyDTO currency);

    void deleteCurrency(CurrencyDTO currency);
}
