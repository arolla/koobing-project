package com.koobing.koobing.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record HostelDto(
        int id,
        String name,
        String address,
        @JsonProperty("available_rooms")
        int availableRooms,
        int price,
        List<String> amenities
){}

