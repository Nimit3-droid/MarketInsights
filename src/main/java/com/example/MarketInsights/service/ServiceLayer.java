package com.example.MarketInsights.service;


import com.example.MarketInsights.VO.*;
import com.example.MarketInsights.dao.MeasurementRepository;
import com.example.MarketInsights.dto.BucketDataDto;
import com.example.MarketInsights.model.Measurement;
import com.example.MarketInsights.model.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class ServiceLayer {
    private final RestTemplate restTemplate;

    //keys and url
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

    public String consumeAPIRef(String state) throws ParseException {
        Records response = restTemplate.getForEntity(
                url+"?api-key="+apiKey+"&format="+format+"&limit="+defaultLimit+"&filters[state]="+state,Records.class
        ).getBody();
        ArrayList<Data> records= (ArrayList<Data>) response.getRecords();
        List<Measurement> measurements = new ArrayList<>();
        int totalCount=0;
        for(Data record: records){
            String strDate = record.getArrival_date();
            String year=strDate.substring(6),month=strDate.substring(3,5),day=strDate.substring(0,2);
            double price = Double.parseDouble(record.getModal_price());
            double min_price=Double.parseDouble((record.getMin_price().equals("NA"))?record.getModal_price():record.getMin_price());
            double max_price=Double.parseDouble((record.getMax_price().equals("NA"))?record.getModal_price():record.getMax_price());
            MetaData metaData = new MetaData(record.getState(), record.getDistrict(), record.getMarket(), record.getCommodity(), record.getVariety());
            Instant timestamp = Instant.parse(year+"-"+month+"-"+day+"T18:00:00.00Z");
            List<Measurement> list=measurementRepository.findByMetaDataTime(metaData,timestamp);
            if(list.size()==0){
                totalCount+=1;
                PriceContainer data=new PriceContainer(price,min_price,max_price);
                measurements.add(new Measurement(timestamp, metaData, data));
            }
        }
        if(measurements.size()!=0)measurementRepository.saveAll(measurements);
        return totalCount+"/"+response.getTotal() + " added Successfully to "+state;
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
//        System.out.println(variety);
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        List<Measurement> mt=measurementRepository.findAll(metaData);
        return mt;
    }

    public Measurement getLastMeasurement(String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        Measurement mt=measurementRepository.findLast(metaData);
        return mt;
    }

    public List<Measurement> getDaysMeasurement(String state,String district,String market,String commodity,String variety,int days){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        List<Measurement> mt=measurementRepository.findForDays(metaData, days);
        return mt;
    }

    public List<BucketDataDto> getAvgMeasurement(String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        List<BucketDataDto> mt=measurementRepository.findAvg(metaData);
        return mt;
    }

    public List<BucketDataDto> getAvgMeasurementInInterval(String start,String end,String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        Instant l = Instant.parse(start+"T18:00:00.00Z");
        Instant r = Instant.parse(end+"T18:00:00.00Z");
        List<BucketDataDto> mt=measurementRepository.findAvgInInterval(metaData,l,r);
        return mt;
    }

    public List<BucketDataDto> getMinMeasurement(String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        List<BucketDataDto> mt=measurementRepository.findMin(metaData);
        return mt;
    }

    public List<BucketDataDto> getMinMeasurementInInterval(String start,String end,String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        Instant l = Instant.parse(start+"T18:00:00.00Z");
        Instant r = Instant.parse(end+"T18:00:00.00Z");
        List<BucketDataDto> mt=measurementRepository.findMinInInterval(metaData,l,r);
        return mt;
    }

    public List<BucketDataDto> getMaxMeasurement(String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        List<BucketDataDto> mt=measurementRepository.findMax(metaData);
        return mt;
    }

    public List<BucketDataDto> getMaxMeasurementInInterval(String start,String end,String state,String district,String market,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,market,commodity,variety);
        Instant l = Instant.parse(start+"T18:00:00.00Z");
        Instant r = Instant.parse(end+"T18:00:00.00Z");
        List<BucketDataDto> mt=measurementRepository.findMaxInInterval(metaData,l,r);
        return mt;
    }
    public List<QueryInsights> getBestMarketPlace(String start,String state,String district,String commodity,String variety){
        MetaData metaData = new MetaData(state,district,"",commodity,variety);
        Instant date = Instant.parse(start+"T18:00:00.00Z");
        List<Measurement> mt=measurementRepository.findMarket(metaData, date);
        List<QueryInsights> markets=new ArrayList<>();
        for(Measurement me:mt){
            markets.add(new QueryInsights(me.getMetaData().market(),me.getData().getPrice()));
        }
        return markets;
    }

    public List<QueryInsights> getBestDistrictMarketPlace(String start,String state,String commodity,String variety){
        MetaData metaData = new MetaData(state,"","",commodity,variety);
        Instant date = Instant.parse(start+"T18:00:00.00Z");
        List<Measurement> mt=measurementRepository.findDistrictMarket(metaData, date);
        List<QueryInsights> markets=new ArrayList<>();
        for(Measurement me:mt){
            String name=me.getMetaData().district()+"-"+me.getMetaData().market();
            double price=me.getData().getPrice();
            markets.add(new QueryInsights(name,price));
        }
        return markets;
    }

    public List<RegionQueries> getDistinctDistricts(String state){
        MetaData metaData = new MetaData(state,"","","","");
        List<RegionQueries> mt=measurementRepository.findDistinctDistrictsByState(metaData);
        return mt;
    }
    public List<RegionQueries> getDistinctMarkets(String state,String district){
        MetaData metaData = new MetaData(state,district,"","","");
        List<RegionQueries> mt=measurementRepository.findDistinctMarketsByDistrict(metaData);
        return mt;
    }
    public List<RegionQueries> getDistinctCommodity(String state,String district,String market){
        MetaData metaData = new MetaData(state,district,market,"","");
        List<RegionQueries> mt=measurementRepository.findDistinctCommodityByMarket(metaData);
        return mt;
    }
    public List<RegionQueries> getDistinctVariety(String state,String district,String market,String commodity){
        MetaData metaData = new MetaData(state,district,market,commodity,"");
        List<RegionQueries> mt=measurementRepository.findDistinctVarietyByCommodity(metaData);
        return mt;
    }

    public List<Commodities> getAllDataInDistrict(String state,String district){
        MetaData metaData = new MetaData(state,district,"","","");
        List<Commodities> mt=measurementRepository.findAllByDistrict(metaData);
        return mt;
    }

    public List<Measurement> getAllAdded(String start) {
        Instant l = Instant.parse(start+"T18:00:00.00Z");
        List<Measurement> list=measurementRepository.findAllByDate(l);
        return list;
    }

    //test
//    public List<Measurement> getIntervals(String start,String end,String state){
//        MetaData metaData = new MetaData(state,"","","","");
//        Instant l = Instant.parse(start+"T18:00:00.00Z");
//        Instant r = Instant.parse(end+"T18:00:00.00Z");
//        List<Measurement> mt=measurementRepository.findInIntervalTest(metaData, l,r);
//        return mt;
//    }



}
