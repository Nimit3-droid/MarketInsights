package com.example.MarketInsights.service;

import com.example.MarketInsights.controller.HomeController;
import com.example.MarketInsights.model.State;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class DataEngine {
    private final HomeController homeController;

    public DataEngine(HomeController homeController) {
        this.homeController = homeController;
    }

//    @Scheduled(cron = "${cron.expression}")
    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    @Async
    public void refreshMarketData() throws ParseException {
        homeController.getDataRef();
        System.out.println("Data Refreshed");
    }
}
