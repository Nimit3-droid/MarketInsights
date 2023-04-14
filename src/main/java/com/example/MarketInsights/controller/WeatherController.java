package com.example.MarketInsights.controller;

import com.example.MarketInsights.VO.CurrentWeather;
import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.VO.WeatherRecord;
import com.example.MarketInsights.model.Measurement;
import com.example.MarketInsights.model.State;
import com.example.MarketInsights.service.WeatherLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherLayer weatherLayer;


    @Autowired
    public WeatherController(WeatherLayer weatherLayer){
        this.weatherLayer=weatherLayer;
    }


    @GetMapping("/realtime/{region}")
    public ResponseEntity<CurrentWeather> getRealTimeWeather(@PathVariable String region) throws ParseException {
        return weatherLayer.getWeatherData(region);
    }
}
