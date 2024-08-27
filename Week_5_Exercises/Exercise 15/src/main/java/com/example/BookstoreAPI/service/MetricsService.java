package com.example.BookstoreAPI.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final Counter bookAccessCounter;

    public MetricsService(MeterRegistry meterRegistry) {

        this.bookAccessCounter = Counter.builder("book.access.count")
                .description("Number of times a book is accessed")
                .register(meterRegistry);
    }

    public void incrementBookAccessCount() {
        bookAccessCounter.increment();
    }
}
