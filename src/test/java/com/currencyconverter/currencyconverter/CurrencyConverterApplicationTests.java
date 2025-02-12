package com.currencyconverter.currencyconverter;

import com.currencyconverter.currencyconverter.config.CurrencyApiConfig;
import com.currencyconverter.currencyconverter.dto.ConversionRequest;
import com.currencyconverter.currencyconverter.dto.ConversionResponse;
import com.currencyconverter.currencyconverter.dto.ExchangeRatesResponse;
import com.currencyconverter.currencyconverter.exception.CurrencyConversionException;
import com.currencyconverter.currencyconverter.service.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CurrencyConverterApplicationTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyApiConfig currencyApiConfig;

    @InjectMocks
    private CurrencyService currencyService;

    private ExchangeRatesResponse mockResponse;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockResponse = new ExchangeRatesResponse();
        mockResponse.setBase("USD");

        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.94);
        rates.put("INR", 83.5);
        mockResponse.setRates(rates);

        when(currencyApiConfig.getApiUrl()).thenReturn("https://openexchangerates.org/api");
        when(currencyApiConfig.getApiKey()).thenReturn("7f720a38bd3e46e389b06f83554940bc");
    }

    @Test
    public void contextLoads() {
        assertNotNull(currencyService);
    }

    @Test
    public void testGetExchangeRates_Success() {
        String url = "https://openexchangerates.org/api/latest.json?app_id=7f720a38bd3e46e389b06f83554940bc&base=USD";

        ResponseEntity<ExchangeRatesResponse> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(url, ExchangeRatesResponse.class)).thenReturn(mockResponseEntity);

        ExchangeRatesResponse response = currencyService.getExchangeRates("USD");

        assertNotNull(response);
        assertEquals("USD", response.getBase());
        assertEquals(0.94, response.getRates().get("EUR"), 0.01);
    }


    @Test
    public void testConvertCurrency_Success() {
        ConversionRequest conversion = new ConversionRequest("USD", "EUR", 100);

        when(restTemplate.getForEntity(anyString(), eq(ExchangeRatesResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ConversionResponse result = currencyService.convertCurrency(conversion);

        assertNotNull(result);
        assertEquals("USD", result.getFrom());
        assertEquals("EUR", result.getTo());
        assertEquals(100, result.getAmount(), 0.01);
        assertEquals(94, result.getConvertedAmount(), 0.01);
    }

    @Test(expected = CurrencyConversionException.class)
    public void testConvertCurrency_InvalidCurrency() {
        currencyService.convertCurrency(new ConversionRequest("USD", "GBP", 100));
    }


}
