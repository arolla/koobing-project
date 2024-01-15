package com.koobing.koobing.search.dto;

import java.util.List;

public record SearchResponse(
        List<HostelDto> hostels
) {
}
