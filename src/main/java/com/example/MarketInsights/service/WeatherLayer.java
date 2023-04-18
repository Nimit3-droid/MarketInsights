package com.example.MarketInsights.service;

import com.example.MarketInsights.VO.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherLayer {
    private final RestTemplate restTemplate;

    //keys and url
    final String url ="https://weatherapi-com.p.rapidapi.com/current.json";
    final String header_key="X-RapidAPI-Key";
    final String header_value="757764f8c7msh656d7a092bc2e36p1a9429jsn6a3edfae429e";
    final String header_host="X-RapidAPI-Host";
    final String header_url="weatherapi-com.p.rapidapi.com";


    @Autowired
    public WeatherLayer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<CurrentWeather> getWeatherData(String region){
        restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor(){
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().set(header_key, header_value);
                request.getHeaders().set(header_host,header_url);
                return execution.execute(request, body);
            }

        });

        ResponseEntity<CurrentWeather> response = restTemplate.getForEntity(url+"?q="+region, CurrentWeather.class);
        return  response;
    }


}
