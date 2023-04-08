package com.example.MarketInsights.model;

import com.example.MarketInsights.VO.PriceContainer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.TimeSeries;
import org.springframework.data.mongodb.core.timeseries.Granularity;

import java.time.Instant;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@RequiredArgsConstructor
@TimeSeries(collection = "measurements",
            timeField = "timestamp", metaField = "metaData",
            granularity = Granularity.HOURS)
public class Measurement {

	@Id
	private String id;
	
	@Field("timestamp")
	private Instant timestamp;
	
	@Field("metaData")
	private MetaData metaData;
	@Field("data")
	private PriceContainer data;


	public Measurement(Instant timestamp, MetaData metaData, PriceContainer data) {
		this.timestamp = timestamp;
		this.metaData = metaData;
		this.data = data;
	}
}
