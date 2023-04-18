package com.example.MarketInsights.controller;

import com.example.MarketInsights.VO.CurrentWeather;
import com.example.MarketInsights.service.WeatherLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin(origins = "${my.property}")
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherLayer weatherLayer;


    @Autowired
    public WeatherController(WeatherLayer weatherLayer){
        this.weatherLayer=weatherLayer;
    }


    /**
     *
     * @param region
     * @return
     * @throws ParseException
     */
    @GetMapping("/realtime")
    public ResponseEntity<CurrentWeather> getRealTimeWeather(@RequestParam String region) throws ParseException {
        return weatherLayer.getWeatherData(region);
    }
}
