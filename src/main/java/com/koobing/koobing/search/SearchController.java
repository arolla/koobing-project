package com.koobing.koobing.search;

import com.koobing.koobing.Either;
import com.koobing.koobing.search.dto.HostelDto;
import com.koobing.koobing.search.dto.SearchResponse;
import com.koobing.koobing.utils.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    private static ResponseEntity<SearchResponse> failedSearch(Either.Left<String, List<Hostel>> e) {
        log.error("[{}] Failed to search for hostels. Cause: {}", Context.correlationId(), e.error());
        return ResponseEntity.badRequest().body(new SearchResponse.Failure(e.error()));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestParam(name = "z") String zipcode,
                                                 @RequestParam(name = "d") String[] dates) {

        log.debug("New request");

        if (dates.length != 2) {
            log.error("Expected 2 dates when hostels search (arrival and departure). Got {} dates.", dates.length);
            return ResponseEntity.badRequest().body(new SearchResponse.Failure("Arrival and departure dates must be provided."));
        }

        log.debug("Searching for hostels in {} between {} and {}", zipcode, dates[0], dates[1]);

        var arrivalDate = LocalDate.parse(dates[0]);
        var departureDate = LocalDate.parse(dates[1]);

        Either<String, List<Hostel>> availableHostels = searchService.availableHostels(new Zipcode(zipcode), arrivalDate, departureDate);
        return switch (availableHostels) {
            case Either.Left<String, List<Hostel>> e -> failedSearch(e);
            case Either.Right<String, List<Hostel>> r -> buildResponse(r.result(), zipcode, dates);

        };
    }

    private ResponseEntity<SearchResponse> buildResponse(List<Hostel> availableHostels, String zipcode, String[] dates) {
        if (availableHostels.isEmpty()) {
            return new ResponseEntity<>(new SearchResponse.NotFound(zipcode, dates[0], dates[1]), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(
                new SearchResponse.Success(
                        // transformation from domain to dto
                        availableHostels.stream().map(HostelDto::from).toList()
                )
        );
    }
}
