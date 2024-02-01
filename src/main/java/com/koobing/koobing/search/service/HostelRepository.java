package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.Zipcode;

import java.time.LocalDate;
import java.util.List;

public interface HostelRepository {
    List<Hostel> availableHostels(Zipcode zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable;
}
