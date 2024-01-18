package com.koobing.koobing.booking;

import com.koobing.koobing.security.SecurityConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({BookingController.class, SecurityConfiguration.class})
public class BookingTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "PROCUREMENT")
    @DisplayName("Book a room")
    void bookRoom() throws Exception {
        var bookingJson = """
                {
                    "hostel_id": 123,
                    "room_id": 324,
                    "arrival": "2020-01-01",
                    "departure": "2020-01-02",
                    "email" : "foo.bar@example.com"
                }
                """;

        var expectedJson = """
                {
                    "booking_number": "A123"
                }
                """;

        mvc.perform(post("/bookings")
                        .contentType("application/json")
                        .content(bookingJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

    @Test
    @WithMockUser
    @DisplayName("A simple user try to book a room")
    void bookRoomBySimpleUser() throws Exception {
        var bookingJson = """
                {
                    "hostel_id": 123,
                    "room_id": 324,
                    "arrival": "2020-01-01",
                    "departure": "2020-01-02",
                    "email" : "foo.bar@example.com"
                }
                """;

        var expectedJson = """
                {
                    "booking_number": "A123"
                }
                """;

        mvc.perform(post("/bookings")
                        .contentType("application/json")
                        .content(bookingJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
