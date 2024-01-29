package com.koobing.koobing.search.repository.jpa;

import com.koobing.koobing.search.Address;
import com.koobing.koobing.search.Hostel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data // lets the lib produce getters and setters
@Table(name = "hostels")
public class HostelEntity {
    @Id
    @Column(name = "hostel_id")
    private Integer id;

    private String name;
    private String street;
    private String zipcode;
    private String city;
    private Float rating;

    @OneToMany
    @JoinColumn(name = "hostel_id")
    private Collection<RoomEntity> rooms = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "amenities", joinColumns = @JoinColumn(name = "hostel_id"))
    @Column(name = "amenity")
    private Set<String> amenities;

    public static Hostel toHostel(HostelEntity e, int roomCount, Integer lowestPrice) {
        return new Hostel(
                e.id,
                e.name,
                new Address(e.street, e.zipcode, e.city),
                roomCount,
                lowestPrice,
                new ArrayList<>(e.amenities));
    }

    public List<RoomEntity> availableRooms(LocalDate arrivalDate, LocalDate departureDate) {
        return rooms.stream()
                .filter(roomEntity -> roomEntity.isAvailable(arrivalDate, departureDate))
                .toList();
    }
}
