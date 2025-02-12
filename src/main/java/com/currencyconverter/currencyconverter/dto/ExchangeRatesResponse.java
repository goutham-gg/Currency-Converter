package com.currencyconverter.currencyconverter.dto;


import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRatesResponse {
    private String base;
    private Map<String, Double> rates;
}
