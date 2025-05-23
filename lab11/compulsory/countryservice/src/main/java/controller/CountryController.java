package com.example.countryservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @GetMapping
    public List<String> getCountries() {
        return Arrays.asList("Romania", "Germany", "France", "Italy", "Spain");
    }
}
