package com.koobing.koobing.search.service;

import com.koobing.koobing.Either;
import com.koobing.koobing.search.Address;
import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchService;
import com.koobing.koobing.search.repository.ResilientSearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class SearchServiceTest {

    @Test
    @DisplayName("Search available hostels")
    void searchAvailableHostels() {
        SearchService searchService = new DefaultSearchService(new StubHostelRepository(false));

        Either<String, List<Hostel>> hostels = searchService.availableHostels("75001", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-02"));

        var expectedHostels = new Either.Right<>(
                List.of(
                        new Hostel(1, "Elegance Hotel", new Address("25 RUE DU LOUVRE", "PARIS", "75001"), 10, 150, List.of("Free Wi-Fi", "Parking", "Complimentary Break")),
                        new Hostel(2, "Charming Inn", new Address("21 RUE DU BOULOI", "PARIS", "75001"), 5, 120, List.of("Free Wi-Fi", "Swimming Pool", "Room Service"))
                )
        );

        assertEquals(expectedHostels, hostels);
    }

    @Test
    @DisplayName("Search available hostels by inverting departure and arrival dates")
    void searchAvailableHostelsWithInvertedDates() {
        SearchService searchService = new DefaultSearchService(new StubHostelRepository(false));

        Either<String, List<Hostel>> hostels = searchService.availableHostels("75001", LocalDate.parse("2024-01-02"), LocalDate.parse("2024-01-01"));

        var expectedHostels = new Either.Right<>(
                List.of(
                        new Hostel(1, "Elegance Hotel", new Address("25 RUE DU LOUVRE", "PARIS", "75001"), 10, 150, List.of("Free Wi-Fi", "Parking", "Complimentary Break")),
                        new Hostel(2, "Charming Inn", new Address("21 RUE DU BOULOI", "PARIS", "75001"), 5, 120, List.of("Free Wi-Fi", "Swimming Pool", "Room Service"))
                )
        );

        assertEquals(expectedHostels, hostels);
    }

    @Test
    @DisplayName("Search available hostels with no night")
    void searchAvailableHostelsWithoutNight() {
        SearchService searchService = new DefaultSearchService(new StubHostelRepository(false));
        Either<String, List<Hostel>> error = searchService.availableHostels("75001", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-01"));

        var expectedError = new Either.Left<>("No night in date range");

        assertEquals(expectedError, error);
    }

    @Test
    @DisplayName("Search available hostels when repository is down")
    void searchAvailableHostelsWithUnavailableRepository() {
        var searchRepository = new ResilientSearchRepository(new StubHostelRepository(true));
        SearchService searchService = new DefaultSearchService(searchRepository);
        Either<String, List<Hostel>> hostels = searchService.availableHostels("75001", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-02"));

        var expectedHostels = new Either.Right<>(Collections.emptyList());

        assertEquals(expectedHostels, hostels);
    }

    @Test
    @DisplayName("Search available hostels when repository is slow")
    void searchAvailableHostelsWithSlowRepository() {
        assertTimeout(Duration.ofMillis(100), () -> {
            var searchRepository = new ResilientSearchRepository(new StubHostelRepository(false, Duration.ofMillis(150)));
            SearchService searchService = new DefaultSearchService(searchRepository);
            Either<String, List<Hostel>> hostels = searchService.availableHostels("75001", LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-02"));

            var expectedHostels = new Either.Right<>(Collections.emptyList());
            assertEquals(expectedHostels, hostels);
        });
    }
}