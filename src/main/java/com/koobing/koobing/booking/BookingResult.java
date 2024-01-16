package com.koobing.koobing.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

public sealed interface BookingResult {
    public record BookingSuccess(@JsonProperty("booking_number") String bookingNumber) implements BookingResult {
    }
}
