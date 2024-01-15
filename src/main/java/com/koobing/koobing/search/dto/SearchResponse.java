package com.koobing.koobing.search.dto;

import java.util.List;

public sealed interface SearchResponse {
    record Success(List<HostelDto> hostels) implements SearchResponse {}
    record Failure(String message) implements SearchResponse {}
}
