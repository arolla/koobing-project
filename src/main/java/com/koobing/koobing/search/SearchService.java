package com.koobing.koobing.search;

import com.koobing.koobing.Either;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    Either<String, List<Hostel>> availableHostels(Zipcode zipcode, LocalDate arrivalDate, LocalDate departureDate);
}
