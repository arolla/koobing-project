package com.koobing.koobing.search;

import io.jbock.util.Either;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    Either<String, List<Hostel>> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate);
}
