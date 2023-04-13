package com.example.MarketInsights.dto;

import java.time.Instant;

public record BucketDataDto(
        Double average,
        Double minPrice,
        Double maxPrice


) {
}
