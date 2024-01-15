package com.koobing.koobing.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public sealed interface SearchResponse {
    record Success(List<HostelDto> hostels) implements SearchResponse {}
    record Failure(String message) implements SearchResponse {}
    record NotFound(
            String zipcode,
            @JsonProperty("arrival_date")
            String arrivalDate,
            @JsonProperty("departure_date")
            String departureDate
    ) implements SearchResponse {}
}
