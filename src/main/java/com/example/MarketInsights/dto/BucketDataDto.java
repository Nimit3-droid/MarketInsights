package com.example.MarketInsights.dto;

import java.time.Instant;

public record BucketDataDto(

        Instant startDate,
        Double average,
        Double last

) {
}
