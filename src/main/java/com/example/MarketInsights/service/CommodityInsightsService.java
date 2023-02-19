package com.example.MarketInsights.service;

import com.example.MarketInsights.dao.CommodityPriceRepository;
import com.example.MarketInsights.dao.CommodityRepository;
import com.example.MarketInsights.model.Commodity;
import com.example.MarketInsights.model.CommodityPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class CommodityInsightsService {

    private final RestTemplate restTemplate;

    @Autowired
    public CommodityInsightsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CommodityPriceRepository commodityPriceRepository;

    //change in price from previous day
    public int findChangeInPrice(String id){
       int changeInPrice=0;
       Commodity commodity = commodityRepository.findById(id).get();
       ArrayList<CommodityPrice> priceList=commodity.getModal_price();
       int sizeOfList = priceList.size();
       int price1 = priceList.get(sizeOfList-1).getPrice();
       int price2 = (sizeOfList-2>=0)?priceList.get(sizeOfList-2).getPrice():0;
       changeInPrice=price1-price2;
       return changeInPrice;
    }



    //max and min price in past d days
    public ArrayList<Integer> findMaxMinPricePastDays(String id, int days){
        Commodity commodity = commodityRepository.findById(id).get();

        ArrayList<CommodityPrice> priceList=commodity.getModal_price();
        int sizeOfList = priceList.size();
        int min=Integer.MAX_VALUE;
        int max=0;
        int index=sizeOfList-1;
        while(index>=Math.max(sizeOfList-days,0)){
            min=Math.min(min,priceList.get(index).getMin_price());
            max=Math.max(max,priceList.get(index).getMax_price());
            index--;
        }
        ArrayList<Integer> max_min=new ArrayList<>();
        max_min.add(max);
        max_min.add(min);
        return max_min;

    }

    //change in price of same date of previous month,year


}
