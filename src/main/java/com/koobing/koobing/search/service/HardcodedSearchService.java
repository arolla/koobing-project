package com.koobing.koobing.search.service;

import com.koobing.koobing.Either;
import com.koobing.koobing.search.Address;
import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchService;
import com.koobing.koobing.search.Zipcode;
import com.koobing.koobing.utils.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Profile("local")
@Slf4j
@Service
public class HardcodedSearchService implements SearchService {
    @Override
    public Either<String, List<Hostel>> availableHostels(Zipcode zipcode, LocalDate arrivalDate, LocalDate departureDate) {
        if (arrivalDate.equals(departureDate)) {
            return new Either.Left<>("No night in date range");
        }

        if (departureDate.isBefore(arrivalDate)) {
            log.warn("[{}] Departure date is before arrival date", Context.correlationId());
        }

        return new Either.Right<>(
                List.of(
                        new Hostel(1, "Elegance Hotel", new Address("25 RUE DU LOUVRE", "PARIS", "75001"), 10, 150, List.of("Free Wi-Fi", "Parking", "Complimentary Breakfast")),
                        new Hostel(2, "Charming Inn", new Address("21 RUE DU BOULOI", "PARIS", "75001"), 5, 120, List.of("Free Wi-Fi", "Swimming Pool", "Room Service"))
                )
        );
    }
}
