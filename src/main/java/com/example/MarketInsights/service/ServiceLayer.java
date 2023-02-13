package com.example.MarketInsights.service;

import com.example.MarketInsights.VO.Data;
import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.dao.CommodityRepository;
import com.example.MarketInsights.model.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {
    private final RestTemplate restTemplate;
    String url="https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070";
    String apiKey="579b464db66ec23bdd0000015709af7f45e048694fedae861885947c";
    String format="json";
    int defaultLimit=10000;
    @Autowired
    public ServiceLayer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private CommodityRepository commodityRepository;

    public Records consumeAPILimit(String limit){
        Records response = restTemplate.getForEntity(
                url+"?api-key="+apiKey+"&format="+format+"&limit="+limit,Records.class
        ).getBody();

        return response;
    }
    public Records consumeAPIState(String state){
        Records response = restTemplate.getForEntity(
                url+"?api-key="+apiKey+"&format="+format+"&limit="+defaultLimit+"&filters[state]="+state,Records.class
        ).getBody();
        ArrayList<Data> records= (ArrayList<Data>) response.getRecords();
        for(Data record: records){
            String id=record.getState()+record.getDistrict()+record.getCommodity()+record.getArrival_date()+ record.getVariety();
            boolean exists = commodityRepository.existsById(id);
            if(exists){
                commodityRepository.findById(id);
                //update
            }
            Commodity commodity=new Commodity(id,record.getState(),record.getDistrict(),record.getCommodity(),record.getArrival_date(), record.getVariety(), record.getMin_price(),record.getMax_price(),record.getModal_price());
            commodityRepository.save(commodity);
        }
        return response;
    }

}
