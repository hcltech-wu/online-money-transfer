package com.callisto.service;

import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CountryService {

    public Map<String, String> getCountry() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream in = getClass().getResourceAsStream("/countries.json")) {
            return mapper.readValue(in, Map.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load country data", e);
        }

    }
}
