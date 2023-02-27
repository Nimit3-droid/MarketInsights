package com.example.MarketInsights.controller;

import com.example.MarketInsights.service.CommodityInsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
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
    public int getCommodityAverage(@PathVariable String id,@PathVariable int days){
        int averagePrice = commodityInsightsService.findAveragePricePastDays(id,days);
        return averagePrice;
    }

}
