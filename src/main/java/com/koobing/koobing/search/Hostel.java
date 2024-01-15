package com.koobing.koobing.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Hostel(
        int id,
        String name,
        Address address,
        int availableRooms,
        int price,
        List<String> amenities

) {
}
