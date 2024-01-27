package com.koobing.koobing.search.repository;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.service.HostelRepository;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Search repository implements the resilient search interface by using delegation to a non-resilient implementation.
 */
public class ResilientSearchRepository implements HostelRepository {
    private final HostelRepository delegate;
    private final TimeLimiter timeLimiter;

    public ResilientSearchRepository(HostelRepository delegate) {
        this.delegate = delegate;
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(80))
                .build();
        this.timeLimiter = TimeLimiter.of("time limiter for hostel search", config);
    }

    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable {
        // Functional style
        CompletableFuture<List<Hostel>> future = CompletableFuture.supplyAsync(() -> {
            try {
                return delegate.availableHostels(zipcode, arrivalDate, departureDate);
            } catch (Throwable e) {
                // Only runtime exceptions are accepter in lambda
                throw new RuntimeException(e);
            }
        });

        return future.get(80, TimeUnit.MILLISECONDS);
    }
}
