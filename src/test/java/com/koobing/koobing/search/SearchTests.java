package com.koobing.koobing.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.*;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(SearchController.class)
public class SearchTests {

    @MockBean
    private SearchService searchService;

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
        var expectedJson = """
                {
                    "message": "Arrival and departure dates must be provided."
                }
                """;

        mvc.perform(get("/search?z=75001&d=2024-01-01"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DisplayName("Search hostel in Paris but no hostel is available")
    void searchInParisButUnavailableHostel() throws Exception {
        given(searchService.availableHostels(anyString(), any(LocalDate.class), any(LocalDate.class)))
                .willReturn(Collections.emptyList());

        var expectedJson = """
                {
                    "zipcode": "75001",
                    "arrival_date": "2024-01-01",
                    "departure_date": "2024-01-02"
                }
                """;

        mvc.perform(get("/search?z=75001&d=2024-01-01&d=2024-01-02"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedJson));
    }
}
