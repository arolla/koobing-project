package com.koobing.koobing.search.repository;

import com.koobing.koobing.search.Address;
import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.service.HostelRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class SqlSearchRepository implements HostelRepository {
    // use template query because of a bug in JDBC H2 driver
    private final static String QUERY = """
            with city_hostels as (select h.*, r.ROOM_ID
                                  from HOSTELS h
                                           join BEDROOMS r on h.HOSTEL_ID = r.HOSTEL_ID
                                  where ZIPCODE = '{ZIPCODE}'),
                 booked_rooms as (select b.ROOM_ID
                                  from BOOKINGS b
                                  where ('{START_DATE}' between b.START_DATE and b.END_DATE)
                                     or '{END_DATE}' between b.START_DATE and b.END_DATE),
                 available_rooms as (select h.HOSTEL_ID, r.ROOM_ID, r.PRICE
                                     from BEDROOMS r
                                              join city_hostels h on r.ROOM_ID = h.ROOM_ID
                                     where not exists (select 1
                                                       from booked_rooms b
                                                       where b.ROOM_ID = r.ROOM_ID)),
                room_count as (select HOSTEL_ID, count(ROOM_ID) as COUNT from available_rooms group by HOSTEL_ID),
                min_price as (select HOSTEL_ID, min(price) as PRICE from available_rooms group by HOSTEL_ID)
            select h.*, c.COUNT, p.PRICE
            from room_count c join min_price p on c.HOSTEL_ID = p.HOSTEL_ID join HOSTELS h on h.HOSTEL_ID = c.HOSTEL_ID
            """;
    private final DataSource dataSource;


    public SqlSearchRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable {
        var jdbc = new JdbcTemplate(dataSource);
        var q = QUERY.replace("{ZIPCODE}", zipcode)
                .replace("{START_DATE}", arrivalDate.toString())
                .replace("{END_DATE}", departureDate.toString());
        return jdbc.query(q,
                (rs, rowNum) -> hostelFromResultSet(rs));
    }

    private Hostel hostelFromResultSet(ResultSet rs) throws SQLException {
        return new Hostel(rs.getInt("HOSTEL_ID"),
                rs.getString("NAME"),
                new Address(rs.getString("STREET"), rs.getString("CITY"), rs.getString("ZIPCODE")),
                rs.getInt("COUNT"),
                rs.getInt("PRICE"),
                Collections.emptyList());
    }
}
