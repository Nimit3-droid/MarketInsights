package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRecord {
    private double temp_c;
    private double temp_f;
    private int is_day;
    private double wind_mph;
    private double wind_kph;
    private double wind_degree;
    private String wind_dir;
    private double pressure_mb;
    private double pressure_in;
    private double precip_mm;
    private double precip_in;
    private double humidity;
    private double cloud;
    private double feelslike_c;
    private double feelslike_f;
    private double vis_km;
    private double vis_miles;
    private double uv;
    private double gust_mph;
    private double gust_kph;
    private WeatherCondition condition;



}
