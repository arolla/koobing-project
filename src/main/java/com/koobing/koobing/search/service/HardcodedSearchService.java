package com.koobing.koobing.search.service;

import com.koobing.koobing.search.Address;
import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchService;
import io.jbock.util.Either;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Profile("local")
@Service
public class HardcodedSearchService implements SearchService {
    @Override
    public Either<String, List<Hostel>> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) {
        return Either.right(
                List.of(
                        new Hostel(1, "Elegance Hotel", new Address("25 RUE DU LOUVRE", "PARIS", "75001"), 10, 150, List.of("Free Wi-Fi", "Parking", "Complimentary Breakfast")),
                        new Hostel(2, "Charming Inn", new Address("21 RUE DU BOULOI", "PARIS", "75001"), 5, 120, List.of("Free Wi-Fi", "Swimming Pool", "Room Service"))
                )
        );
    }
}
