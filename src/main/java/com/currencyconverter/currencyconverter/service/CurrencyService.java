package com.currencyconverter.currencyconverter.service;


import com.currencyconverter.currencyconverter.config.CurrencyApiConfig;
import com.currencyconverter.currencyconverter.dto.ConversionRequest;
import com.currencyconverter.currencyconverter.dto.ConversionResponse;
import com.currencyconverter.currencyconverter.dto.CurrencyConversion;
import com.currencyconverter.currencyconverter.dto.ExchangeRatesResponse;
import com.currencyconverter.currencyconverter.exception.CurrencyConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyApiConfig currencyApiConfig;

    public ExchangeRatesResponse getExchangeRates(String baseCurrency) {
        String url = currencyApiConfig.getApiUrl() + "/latest.json?app_id="
                + currencyApiConfig.getApiKey() + "&base=" + baseCurrency;

        try {
            ResponseEntity<ExchangeRatesResponse> response = restTemplate.getForEntity(url, ExchangeRatesResponse.class);

            if (response == null || response.getBody() == null) {
                throw new CurrencyConversionException("Failed to fetch exchange rates: API response is null");
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new CurrencyConversionException("Failed to fetch exchange rates: " + e.getResponseBodyAsString());
        }
    }


    public ConversionResponse convertCurrency(ConversionRequest request) {
        ExchangeRatesResponse response = getExchangeRates(request.getFrom());

        if (response == null || response.getRates() == null || !response.getRates().containsKey(request.getTo())) {
            throw new CurrencyConversionException("Invalid currency or API response");
        }

        double rate = response.getRates().get(request.getTo());
        return new ConversionResponse(request.getFrom(), request.getTo(), request.getAmount(), request.getAmount() * rate);
    }


}

