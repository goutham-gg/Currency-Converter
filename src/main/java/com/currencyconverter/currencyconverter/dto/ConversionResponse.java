package com.currencyconverter.currencyconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {
    private String from;
    private String to;
    private double amount;
    private double convertedAmount;
}
