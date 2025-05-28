package com.callisto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.callisto.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @InjectMocks
    private CountryService countryService;

    private ObjectMapper mockMapper;

    @BeforeEach
    void setUp() {
        countryService = new CountryService();
    }

    @Test
    void testCountryService() {
        Map<String, String> countries = countryService.getCountry();
        assertNotNull(countries);
        assertEquals("USA", countries.get("US"));
    }

}
