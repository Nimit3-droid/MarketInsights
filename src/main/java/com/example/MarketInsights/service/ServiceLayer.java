package com.example.MarketInsights.service;


import com.example.MarketInsights.VO.PriceContainer;
import com.example.MarketInsights.VO.Data;
import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.dao.MeasurementRepository;
import com.example.MarketInsights.model.Measurement;
import com.example.MarketInsights.model.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
    private MeasurementRepository measurementRepository;

    public Records consumeAPIRef() throws ParseException {
        Records response = restTemplate.getForEntity(
                url+"?api-key="+apiKey+"&format="+format+"&limit="+defaultLimit+"&filters[state]=Kerala",Records.class
        ).getBody();
        ArrayList<Data> records= (ArrayList<Data>) response.getRecords();
        List<Measurement> measurements = new ArrayList<>();
        for(Data record: records){
//            String id=record.getState()+record.getDistrict()+record.getMarket()+record.getCommodity()+record.getVariety();
            String strDate = record.getArrival_date();
            String year=strDate.substring(6),month=strDate.substring(3,5),day=strDate.substring(0,2);
            int price = Integer.parseInt(record.getModal_price());
            int min_price=Integer.parseInt((record.getMin_price().equals("NA"))?record.getModal_price():record.getMin_price());
            int max_price=Integer.parseInt((record.getMax_price().equals("NA"))?record.getModal_price():record.getMax_price());
            MetaData metaData = new MetaData(record.getState(), record.getDistrict(), record.getMarket(), record.getCommodity(), record.getVariety());

            Instant timestamp = Instant.parse(year+"-"+month+"-"+day+"T18:00:00.00Z");
            PriceContainer data=new PriceContainer(price,min_price,max_price);
            measurements.add(new Measurement(timestamp, metaData, data));
        }
        measurementRepository.saveAll(measurements);
        return response;
    }




    public void testAPI(){
        MetaData metaData = new MetaData("Raj", "Jai","Tonk","Apple","Large");

        // create 1000 measurements from 1 to 1000
        List<Measurement> measurements = new ArrayList<>(5);

        Instant timestamp = Instant.parse("2022-11-03T18:00:00.00Z");
        float value = 100000;

        for (int i = 5; i > 0; i--) {
            PriceContainer data=new PriceContainer(i,i-10,i+10);
            measurements.add(new Measurement(timestamp, metaData, data));
            timestamp = timestamp.plus(24, ChronoUnit.HOURS);
            value--;
        }

        measurementRepository.saveAll(measurements);
    }

    public List<Measurement> getQueryInRange(String start,String end,String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        Instant l = Instant.parse(start+"T18:00:00.00Z");
        Instant r = Instant.parse(end+"T18:00:00.00Z");
        List<Measurement> measurements = new ArrayList<>();
        List<Measurement> mt=measurementRepository.findInInterval(metaData,l,r);
        return mt;
    }

    public List<Measurement> getAllPrice(String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        List<Measurement> mt=measurementRepository.findAllData(metaData);
        return mt;
    }


}
