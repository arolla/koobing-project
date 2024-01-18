package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Address;
import com.koobing.koobing.search.Hostel;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class StubHostelRepository implements HostelRepository {
    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) {
        if (zipcode.equals("75001") && arrivalDate.equals(LocalDate.parse("2024-01-01")) && departureDate.equals(LocalDate.parse("2024-01-02"))) {
            return List.of(
                    new Hostel(1, "Elegance Hotel", new Address("25 RUE DU LOUVRE", "PARIS", "75001"), 10, 150, List.of("Free Wi-Fi", "Parking", "Complimentary Break")),
                    new Hostel(2, "Charming Inn", new Address("21 RUE DU BOULOI", "PARIS", "75001"), 5, 120, List.of("Free Wi-Fi", "Swimming Pool", "Room Service"))
            );
        }

        return Collections.emptyList();
    }
}
