package com.example.BookstoreAPI.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    public CustomMetrics(MeterRegistry meterRegistry) {
        meterRegistry.gauge("custom_metric", this, CustomMetrics::getCustomMetric);
    }

    public double getCustomMetric() {
        return Math.random();
    }
}
