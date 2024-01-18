package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchService;
import io.jbock.util.Either;

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
            return Either.left("No night in date range");
        }
        if (arrivalDate.isAfter(departureDate)) {
            return Either.right(hostelRepository.availableHostels(zipcode, departureDate, arrivalDate));
        }
        return Either.right(hostelRepository.availableHostels(zipcode, arrivalDate, departureDate));
    }
}
