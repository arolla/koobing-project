package com.koobing.koobing.search.repository;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.service.HostelRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Search repository implements the resilient search interface by using delegation to a non-resilient implementation.
 */
public class ResilientSearchRepository implements HostelRepository {
    private final HostelRepository delegate;

    public ResilientSearchRepository(HostelRepository delegate) {
        this.delegate = delegate;

    }

    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable {
        return delegate.availableHostels(zipcode, arrivalDate, departureDate);
    }
}
