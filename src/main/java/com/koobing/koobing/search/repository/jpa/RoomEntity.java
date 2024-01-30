package com.koobing.koobing.search.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "bedrooms")
@Data // lets the lib produce getters and setters
public class RoomEntity {
    @Id
    @Column(name = "room_id")
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Collection<BookingEntity> bookings = new ArrayList<>();

    private Integer price;

    public boolean isAvailable(LocalDate arrivalDate, LocalDate departureDate) {
        return bookings.stream()
                .allMatch(bookingEntity -> departureDate.isAfter(bookingEntity.getEndDate()) && arrivalDate.isBefore(bookingEntity.getStartDate()));
    }
}
