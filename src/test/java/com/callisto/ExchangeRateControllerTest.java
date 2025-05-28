package com.callisto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.callisto.controller.ExchangeRateController;
import com.callisto.service.ExchangeRateService;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateControllerTest {
    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @Test
    void testExchangeRateList() {
        Map<String, String> mockRates = Map.of("IN", "74.85");
        when(exchangeRateService.getExchangeRate()).thenReturn(mockRates);
        String result = exchangeRateController.getexchangeRateList("IN").get("IN");
        assertEquals("74.85", result);
    }

}
