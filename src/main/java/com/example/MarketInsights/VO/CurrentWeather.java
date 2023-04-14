package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentWeather {
    private WeatherRecord current;
    private WeatherLocation location;

}
