package com.koobing.koobing.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
public class SearchTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Search hostel in Paris")
    void searchInParis() throws Exception {
        var expectedJson = """
                {
                  "hostels": [
                    {
                      "id": 1,
                      "name": "Elegance Hotel",
                      "address": "25 RUE DU LOUVRE, 75001, PARIS",
                      "available_rooms": 10,
                      "price": 150,
                      "amenities": ["Free Wi-Fi", "Parking", "Complimentary Breakfast"]
                    },
                    {
                      "id": 2,
                      "name": "Charming Inn",
                      "address": "21 RUE DU BOULOI, 75001, PARIS",
                      "available_rooms": 5,
                      "price": 120,
                      "amenities": ["Free Wi-Fi", "Swimming Pool", "Room Service"]
                    }
                  ]
                }
                                """;

        mvc.perform(get("/search?z=75001&d=2024-01-01&d=2024-01-02"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

    @Test
    @DisplayName("Search hostel in Paris without zipcode")
    void searchInParisWithoutZipcode() throws Exception {
        mvc.perform(get("/search?d=2024-01-01&d=2024-01-02"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("Search hostel in Paris without date")
    void searchInParisWithoutDate() throws Exception {
        mvc.perform(get("/search?z=75001"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Search hostel in Paris with one date missing")
    void searchInParisWithOneMissingDate() throws Exception {
        mvc.perform(get("/search?z=75001&d=2024-01-01"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
