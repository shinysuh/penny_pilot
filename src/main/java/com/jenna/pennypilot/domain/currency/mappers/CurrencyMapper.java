package com.jenna.pennypilot.domain.currency.mappers;

import com.jenna.pennypilot.domain.currency.dtos.CurrencyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CurrencyMapper {

    List<CurrencyDTO> selectAllCurrencies();

    CurrencyDTO selectOneCurrencyByCode(String code);

    void addCurrency(CurrencyDTO currency);

    void deleteCurrency(CurrencyDTO currency);

}
