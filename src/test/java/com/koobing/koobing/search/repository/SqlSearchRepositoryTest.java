package com.koobing.koobing.search.repository;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.SearchConfiguration;
import com.koobing.koobing.search.Zipcode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import({SearchConfiguration.class})
@Sql(scripts = {"/sql/create-tables.sql", "/sql/insert.sql"})
@Sql(scripts = {"/sql/drop-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("jdbc")
class SqlSearchRepositoryTest {

    @Autowired
    private SqlSearchRepository repository;

    @Test
    @DisplayName("search available hostels from DB")
    void availableHostels() throws Throwable {
        List<Hostel> hostels = repository.availableHostels(new Zipcode("49000"), LocalDate.of(2024, 2, 5), LocalDate.of(2024, 2, 6));
        assertEquals(1, hostels.size());
        // getFirst() since Java 21
        assertEquals("Charmant Château", hostels.getFirst().name());
        assertEquals(4, hostels.getFirst().availableRooms());
        assertEquals(105, hostels.getFirst().price());
    }
}