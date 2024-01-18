package com.koobing.koobing.search.service;

import com.koobing.koobing.Either;
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
    public Either<String, List<Hostel>> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) {
        if (arrivalDate.equals(departureDate)) {
            return new Either.Left<>("No night in date range");
        }

        List<Hostel> hostels;
        if (arrivalDate.isAfter(departureDate)) {
            hostels = hostelRepository.availableHostels(zipcode, departureDate, arrivalDate);
        } else {
            hostels = hostelRepository.availableHostels(zipcode, arrivalDate, departureDate);
        }

        return new Either.Right<>(hostels);
    }
}
