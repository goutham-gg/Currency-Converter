package com.currencyconverter.currencyconverter.controller;

import com.currencyconverter.currencyconverter.dto.ConversionRequest;
import com.currencyconverter.currencyconverter.dto.ConversionResponse;
import com.currencyconverter.currencyconverter.service.CurrencyService;
import com.currencyconverter.currencyconverter.dto.ExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/rates")
    public ExchangeRatesResponse getRates(@RequestParam(defaultValue = "USD") String base) {
        return currencyService.getExchangeRates(base);
    }

    @PostMapping("/convert")
    public ConversionResponse convertCurrency(@RequestBody ConversionRequest conversion) {
        return currencyService.convertCurrency(conversion);
    }
}

