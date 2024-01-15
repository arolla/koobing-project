package com.koobing.koobing.search;

import lombok.Data;

import java.util.List;

public record SearchResponse(
        List<Hostel> hostels
) {
}
