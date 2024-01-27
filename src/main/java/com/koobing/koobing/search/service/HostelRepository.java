package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Hostel;

import java.time.LocalDate;
import java.util.List;

public interface HostelRepository {
    List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable;
}
