package com.example.MarketInsights.model;

import org.springframework.data.mongodb.core.mapping.Field;

public record MetaData(

		@Field("state")
		String state,

		@Field("district")
		String district,
		@Field("market")
		String market,
		@Field("commodity")
		String commodity,
		@Field("variety")
		String variety


) { }
