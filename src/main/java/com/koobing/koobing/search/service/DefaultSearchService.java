package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchService;

import java.time.LocalDate;
import java.util.List;

public class DefaultSearchService implements SearchService {
    private final HostelRepository hostelRepository;

    public DefaultSearchService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) {
        return hostelRepository.availableHostels(zipcode, arrivalDate, departureDate);
    }
}
