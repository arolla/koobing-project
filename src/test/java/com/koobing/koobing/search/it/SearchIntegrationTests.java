package com.koobing.koobing.search.it;

import com.koobing.koobing.search.dto.SearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Random port to avoid conflicts with other tests
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnabledIfEnvironmentVariable(named = "KOOBING_INTEGRATION_TEST", matches = "true")
@ActiveProfiles({"local", "jdbc"})
public class SearchIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    @Test
    @DisplayName("Search hostels in Angers with anonynmous user is blocked")
    void searchHostelsWithAnonymousUser() {
        var resp = client.getForEntity("/search?z=49000&d=2024-01-01&d=2024-01-02", SearchResponse.Success.class);
        assertEquals(401, resp.getStatusCode().value());
    }

    @Test
    @DisplayName("Search hostels in Angers with user")
    void searchHostelsWithUser() {
        var resp = client
                .withBasicAuth("user1", "password1")
                .getForEntity("/search?z=49000&d=2024-01-01&d=2024-01-02", SearchResponse.Success.class);
        assertEquals(200, resp.getStatusCode().value());

        var expectedNumberOfHostels = 2;
        assertEquals(expectedNumberOfHostels, resp.getBody().hostels().size());
    }
}

