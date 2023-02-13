package com.example.MarketInsights.controller;

import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.dao.CommodityRepository;
import com.example.MarketInsights.model.Commodity;
import com.example.MarketInsights.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    private final ServiceLayer serviceLayer;

    @Autowired
    public HomeController(ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

//    @Autowired
//    private CommodityRepository commodityRepository;
//
//    @GetMapping("/getCommodity")
//    public List<Commodity> getCommodity(){
//        return commodityRepository.findAll();
//    }
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

}
