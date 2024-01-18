package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Address;
import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchServiceTest {

    @Test
    @DisplayName("Search available hostels")
    void searchAvailableHostels() {
        SearchService searchService = new DefaultSearchService();

        List<Hostel> hostels = searchService.availableHostels("75001", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-02"));

        var expectedHostels = List.of(
                new Hostel(1, "Elegance Hotel", new Address("25 RUE DU LOUVRE", "PARIS", "75001"), 10, 150, List.of("Free Wi-Fi", "Parking", "Complimentary Break")),
                new Hostel(2, "Charming Inn", new Address("21 RUE DU BOULOI", "PARIS", "75001"), 5, 120, List.of("Free Wi-Fi", "Swimming Pool", "Room Service"))
        );

        assertEquals(expectedHostels, hostels);
    }
}