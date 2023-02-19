package com.example.MarketInsights.controller;

import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.dao.CommodityRepository;
import com.example.MarketInsights.model.Commodity;
import com.example.MarketInsights.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class HomeController {
    private final ServiceLayer serviceLayer;

    @Autowired
    public HomeController(ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

    @Autowired
    private CommodityRepository commodityRepository;

    @GetMapping("/all/{limit}")
    public ResponseEntity<Records> getDataLimit(@PathVariable String limit){
        ResponseEntity<Records> records = new ResponseEntity<Records>(serviceLayer.consumeAPILimit(limit), HttpStatus.OK);

        return records;
    }

    @GetMapping("/state/{stateName}")
    public ResponseEntity<Records> getDataState(@PathVariable String stateName){
        ResponseEntity<Records> records = new ResponseEntity<Records>(serviceLayer.consumeAPIState(stateName), HttpStatus.OK);
        return records;
    }

    @GetMapping("/refresh")
    public String getDataRef() throws ParseException {
        ResponseEntity<Records> records = new ResponseEntity<>(serviceLayer.consumeAPIRef(),HttpStatus.OK);
        return "Commodity Refreshed";
    }

    @GetMapping("/api/getCommodity")
    public List<Commodity> getCommodity(){
        return commodityRepository.findAll();
    }

    @GetMapping("/api/getCommodity/{state}")
    public List<Commodity> getCommodityState(@PathVariable String state){
        return commodityRepository.getCommodityByState(state);
    }
    @GetMapping("/api/getCommodity/{state}/{district}")
    public List<Commodity> getCommodityDistrict(@PathVariable String state,@PathVariable String district){
        return commodityRepository.getCommodityByDistrict(state,district);
    }

    @GetMapping("/api/getCommodity/{state}/{district}/{market}")
    public List<Commodity> getCommodityMarket(@PathVariable String state,@PathVariable String district, @PathVariable String market){
        return commodityRepository.getCommodityByMarket(state,district,market);
    }

    @GetMapping("/api/getCommodity/{state}/{district}/{market}/{commodity}")
    public List<Commodity> getCommodityName(@PathVariable String state,@PathVariable String district, @PathVariable String market,@PathVariable String commodity){
        return commodityRepository.getCommodityByName(state,district,market,commodity);
    }

    @GetMapping("/api/getCommodity/{state}/{district}/{market}/{commodity}/{variety}")
    public List<Commodity> getCommodityName(@PathVariable String state,@PathVariable String district, @PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        return commodityRepository.getCommodityByVariety(state,district,market,commodity,variety);
    }


}
