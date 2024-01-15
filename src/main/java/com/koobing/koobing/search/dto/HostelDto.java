package com.koobing.koobing.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.koobing.koobing.search.Hostel;

import java.util.List;

public record HostelDto(
        int id,
        String name,
        String address,
        @JsonProperty("available_rooms")
        int availableRooms,
        int price,
        List<String> amenities
) {
    static public HostelDto from(Hostel hostel) {
        return new HostelDto(
                hostel.id(),
                hostel.name(),
                String.format("%s, %s, %s", hostel.address().street(), hostel.address().zipcode(), hostel.address().city()),
                hostel.availableRooms(),
                hostel.price(),
                hostel.amenities());
    }
}

