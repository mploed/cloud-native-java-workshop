package com.innoq.cloudnative.creditapp.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class JoshLongHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.down().withDetail("Presence", "Josh is not in Mainz").build();
    }
}
