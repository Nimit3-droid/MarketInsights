package com.example.MarketInsights.model;

import com.example.MarketInsights.dao.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommodityController {
    @Autowired
    private CommodityRepository commodityRepository;

    @PostMapping("/addCommodity")
    public String addCommodity(@RequestBody Commodity commodity){
        commodityRepository.save(commodity);
        return "commodity has been saved" + commodity.getId();
    }
    @GetMapping("/getCommodity")
    public List<Commodity> getCommodity(){
        return commodityRepository.findAll();
    }
}
