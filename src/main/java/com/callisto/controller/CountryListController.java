package com.callisto.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.callisto.service.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountryListController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public Map<String, String> getCountryList() {
        return countryService.getCountry();
    }

}
