package com.koobing.koobing.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class SearchController {

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestParam(name = "z") String zipcode,
                                                 @RequestParam(name = "d") String[] dates) {
        log.info("Searching for hostels in {} between {} and {}", zipcode, dates[0], dates[1]);
        return ResponseEntity.ok(
                new SearchResponse(
                        List.of(
                                new Hostel(1, "Elegance Hotel", "25 RUE DU LOUVRE, 75001, PARIS", 10, 150, List.of("Free Wi-Fi", "Parking", "Complimentary Breakfast")),
                                new Hostel(2, "Charming Inn", "21 RUE DU BOULOI, 75001, PARIS", 5, 120, List.of("Free Wi-Fi", "Swimming Pool", "Room Service"))
                        )
                )
        );
    }
}
