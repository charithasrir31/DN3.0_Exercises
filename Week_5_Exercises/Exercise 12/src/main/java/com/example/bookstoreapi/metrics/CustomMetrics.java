package com.example.bookstoreapi.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    private final Counter bookCreationCounter;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.bookCreationCounter = meterRegistry.counter("book_creation_total");
    }

    public void incrementBookCreation() {
        bookCreationCounter.increment();
    }
}
