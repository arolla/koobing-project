package com.koobing.koobing.search;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate);
}
