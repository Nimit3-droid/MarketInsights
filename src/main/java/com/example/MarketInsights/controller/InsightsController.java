package com.example.MarketInsights.controller;

import com.example.MarketInsights.service.CommodityInsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/insights")
public class InsightsController {
    private final CommodityInsightsService commodityInsightsService;

    @Autowired
    public InsightsController(CommodityInsightsService commodityInsightsService) {
        this.commodityInsightsService = commodityInsightsService;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/change/{id}")
    public int getCommodityId(@PathVariable String id){
        int valueChanged = commodityInsightsService.findChangeInPrice(id);
        return valueChanged;
    }

    /**
     *
     * @param id
     * @param days
     * @return
     */
    @GetMapping("/max_min/{id}/{days}")
    public ArrayList<Integer> getCommodityMax_Min(@PathVariable String id, @PathVariable int days){
        ArrayList<Integer> max_min = commodityInsightsService.findMaxMinPricePastDays(id,days);
        return max_min;
    }

    /**
     *
     * @param id
     * @param days
     * @return
     */
    @GetMapping("/average/{id}/{days}")
    public double getCommodityAverage(@PathVariable String id,@PathVariable int days){
        double averagePrice = commodityInsightsService.findAveragePricePastDays(id,days);
        return averagePrice;
    }

}
