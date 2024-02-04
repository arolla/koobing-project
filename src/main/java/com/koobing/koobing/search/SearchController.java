package com.koobing.koobing.search;

import com.koobing.koobing.Either;
import com.koobing.koobing.search.dto.HostelDto;
import com.koobing.koobing.search.dto.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Search for hostels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Hostel List",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResponse.Success.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Something went wrong",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResponse.Failure.class))})
    })
    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestParam(name = "z") @Parameter(name = "zipcode") String zipcode,
                                                 @RequestParam(name = "d") @Parameter(name = "date", description = "Give two values: one for arrival and another for departure") String[] dates) {
        if (dates.length != 2) {
            return ResponseEntity.badRequest().body(new SearchResponse.Failure("Arrival and departure dates must be provided."));
        }

        log.info("Searching for hostels in {} between {} and {}", zipcode, dates[0], dates[1]);

        var arrivalDate = LocalDate.parse(dates[0]);
        var departureDate = LocalDate.parse(dates[1]);

        Either<String, List<Hostel>> availableHostels = searchService.availableHostels(new Zipcode(zipcode), arrivalDate, departureDate);
        return switch (availableHostels) {
            case Either.Left<String, List<Hostel>> e ->
                    ResponseEntity.badRequest().body(new SearchResponse.Failure(e.error()));
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
