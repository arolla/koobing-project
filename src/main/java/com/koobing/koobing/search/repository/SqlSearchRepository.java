package com.koobing.koobing.search.repository;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.service.HostelRepository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class SqlSearchRepository implements HostelRepository {
    private final DataSource dataSource;

    public SqlSearchRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable {
        return Collections.emptyList();
    }
}
