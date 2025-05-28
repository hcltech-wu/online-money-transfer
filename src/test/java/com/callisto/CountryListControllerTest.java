package com.callisto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.callisto.controller.CountryListController;
import com.callisto.service.CountryService;

@ExtendWith(MockitoExtension.class)
public class CountryListControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryListController countryListController;

    @Test
    void testCountryListController() {
        Map<String, String> mockCountryMap = Map.of("IN", "India", "US", "United States");
        when(countryService.getCountry()).thenReturn(mockCountryMap);
        Map<String, String> result = countryListController.getCountryList();
        assertEquals(2, result.size());
        assertEquals("India", result.get("IN"));

    }

}
