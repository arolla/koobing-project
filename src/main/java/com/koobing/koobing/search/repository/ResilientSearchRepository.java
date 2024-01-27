package com.koobing.koobing.search.repository;

import com.koobing.koobing.search.Hostel;
import com.koobing.koobing.search.service.HostelRepository;
import io.github.resilience4j.core.functions.CheckedSupplier;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

/**
 * Search repository implements the resilient search interface by using delegation to a non-resilient implementation.
 */
public class ResilientSearchRepository implements HostelRepository {
    private final Retry retry;
    private final HostelRepository delegate;

    public ResilientSearchRepository(HostelRepository delegate) {
        this.delegate = delegate;
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();

        this.retry = Retry.of("Basic search retry", retryConfig);
    }

    @Override
    public List<Hostel> availableHostels(String zipcode, LocalDate arrivalDate, LocalDate departureDate) throws Throwable {
        CheckedSupplier<List<Hostel>> decorate = Decorators.ofCheckedSupplier(() -> delegate.availableHostels(zipcode, arrivalDate, departureDate))
                .withRetry(retry)
                .decorate();

        return decorate.get();
    }
}
