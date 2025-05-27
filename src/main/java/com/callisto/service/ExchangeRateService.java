package com.callisto.service;

import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExchangeRateService {

    public Map<String, String> getExchangeRate() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream in = getClass().getResourceAsStream("/exchangerate.json")) {
            return mapper.readValue(in, Map.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load ExchangeRate data", e);
        }

    }

}
