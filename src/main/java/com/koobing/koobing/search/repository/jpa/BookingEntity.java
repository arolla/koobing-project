package com.koobing.koobing.search.repository.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data // lets the lib produce getters and setters
public class BookingEntity {
    @Id
    @Column(name = "booking_id")
    private UUID id;
    private String guestEmail;
    //    private int roomNumber;
    private LocalDate startDate;
    private LocalDate endDate;
}
