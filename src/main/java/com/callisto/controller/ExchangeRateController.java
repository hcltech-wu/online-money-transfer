package com.callisto.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.callisto.service.ExchangeRateService;

@RestController
@RequestMapping("/api/exchangerate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping
    public Map<String, String> getexchangeRateList(@RequestParam("country") String country) {
        Map<String, String> rates = exchangeRateService.getExchangeRate();
        if (!country.isEmpty()) {
            return rates;
        } else {
            throw new IllegalArgumentException("Country not found: " + country);
        }

    }
}
