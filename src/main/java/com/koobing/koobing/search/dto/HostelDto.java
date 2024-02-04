package com.koobing.koobing.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.koobing.koobing.search.Hostel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record HostelDto(
        @Schema(description = "Hostel ID", example = "1")
        int id,
        @Schema(description = "Hostel Name", example = "Charmant Château")
        String name,
        @Schema(description = "Hostel Full address", example = "123 Rue de la gare, 49000, Angers")
        String address,
        @JsonProperty("available_rooms")
        @Schema(description = "Number of available rooms", example = "4")
        int availableRooms,
        @Schema(description = "Minimal hostel room price", example = "105")
        int price,
        @Schema(description = "Hostel Amenities", example = "wifi, parking")
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

