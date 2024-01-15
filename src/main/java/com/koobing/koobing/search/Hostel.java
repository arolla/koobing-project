package com.koobing.koobing.search;

import java.util.List;

public record Hostel(
        int id,
        String name,
        String address,
        int availableRooms,
        int price,
        List<String> amenedities
){}

