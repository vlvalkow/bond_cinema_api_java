package dev.vladimirvalkov.api.bondcinema.app;

import java.time.Instant;

public record Booking(
        int id,
        String customerName,
        String movie,
        int seats,
        Instant createdAt
) {}
