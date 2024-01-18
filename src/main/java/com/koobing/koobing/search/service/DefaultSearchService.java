package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchService;

import java.time.LocalDate;
import java.util.List;

public class DefaultSearchService implements SearchService {
    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) {
        return null;
    }
}
