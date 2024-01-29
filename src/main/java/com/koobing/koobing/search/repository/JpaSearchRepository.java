package com.koobing.koobing.search.repository;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.repository.jpa.HostelEntity;
import com.koobing.koobing.search.repository.jpa.HostelJpaRepository;
import com.koobing.koobing.search.repository.jpa.RoomEntity;
import com.koobing.koobing.search.service.HostelRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class JpaSearchRepository implements HostelRepository {
    private final HostelJpaRepository hostelJpaRepository;

    public JpaSearchRepository(HostelJpaRepository hostelJpaRepository) {
        this.hostelJpaRepository = hostelJpaRepository;
    }

    @Override
    /*
    Not the best implementation, but we take a look at:
    - The magic Spring Data JPA interface management
    - Some streams
    - Static factory method from DTO to Domain
     */
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable {
        // all hostels in angers
        // -> find hostels in angers with at least one available room

        var hostels = hostelJpaRepository.findByZipcode(zipcode);

        List<HostelEntity> availableHostels = hostels.stream()
                .filter(entity -> !entity.availableRooms(arrivalDate, departureDate).isEmpty())
                .toList();

        return availableHostels.stream()
                .map(hostelEntity -> {
                    var rooms = hostelEntity.availableRooms(arrivalDate, departureDate);
                    RoomEntity firstRoom = rooms.stream()
                            .min(Comparator.comparing(RoomEntity::getPrice))
                            .orElseThrow();
                    return HostelEntity.toHostel(hostelEntity, rooms.size(), firstRoom.getPrice());
                })
                .toList();
    }
}
