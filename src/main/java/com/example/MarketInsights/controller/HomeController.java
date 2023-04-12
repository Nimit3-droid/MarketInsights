package com.example.MarketInsights.controller;


import com.example.MarketInsights.VO.QueryResult;
import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.model.Measurement;
import com.example.MarketInsights.model.MetaData;
import com.example.MarketInsights.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class HomeController {
    private final ServiceLayer serviceLayer;

    @Autowired
    public HomeController(ServiceLayer serviceLayer) {
        this.serviceLayer = serviceLayer;
    }

    @GetMapping("/")
    public String getDataLimit(){
        return "Home";
    }

    @GetMapping("/save")
    public String testAPICont(){
        serviceLayer.testAPI();
        return "Home";
    }

    @GetMapping("/queryRange/{startDate}/{endDate}/{state}/{district}/{market}/{commodity}/{variety}")
    public QueryResult queryInRange(@PathVariable String startDate,@PathVariable String endDate,@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getQueryInRange(startDate,endDate,state,district,market,commodity,variety), HttpStatus.OK).getBody();
        if(records.size()==0){
            return new QueryResult();
        }

        List<Instant> dates=new ArrayList<>();
        List<Double> prices=new ArrayList<>();
        List<Double> max_prices=new ArrayList<>();
        List<Double> min_prices=new ArrayList<>();
        for(Measurement measure:records){
            dates.add(measure.getTimestamp());
            prices.add(measure.getData().getPrice());
            max_prices.add(measure.getData().getMax_pr());
            min_prices.add(measure.getData().getMin_pr());
        }
        MetaData metaData=records.get(0).getMetaData();
        String stateName=metaData.state();
        String districtName=metaData.district();
        String marketName=metaData.market();
        String commodityName=metaData.commodity();
        String varietyName=metaData.variety();
        QueryResult qr=new QueryResult(dates,stateName,districtName, marketName, commodityName, varietyName,min_prices,max_prices,prices);
        return qr;
    }

    @GetMapping("/queryAll/{state}/{district}/{market}/{commodity}/{variety}")
    public QueryResult queryAllPriceList(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getAllPrice(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        if(records.size()==0){
            return new QueryResult();
        }
        List<Instant> dates=new ArrayList<>();
        List<Double> prices=new ArrayList<>();
        List<Double> max_prices=new ArrayList<>();
        List<Double> min_prices=new ArrayList<>();
        for(Measurement measure:records){
            dates.add(measure.getTimestamp());
            prices.add(measure.getData().getPrice());
            max_prices.add(measure.getData().getMax_pr());
            min_prices.add(measure.getData().getMin_pr());
        }
        MetaData metaData=records.get(0).getMetaData();
        String stateName=metaData.state();
        String districtName=metaData.district();
        String marketName=metaData.market();
        String commodityName=metaData.commodity();
        String varietyName=metaData.variety();
        QueryResult qr=new QueryResult(dates,stateName,districtName, marketName, commodityName, varietyName,min_prices,max_prices,prices);
        return qr;
    }

    @GetMapping("/getLast/{state}/{district}/{market}/{commodity}/{variety}")
    public QueryResult queryLast(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        Measurement record = new ResponseEntity<Measurement>(serviceLayer.getLastMeasurement(state,district,market,commodity,variety), HttpStatus.OK).getBody();

        List<Instant> dates=new ArrayList<>();
        List<Double> prices=new ArrayList<>();
        List<Double> max_prices=new ArrayList<>();
        List<Double> min_prices=new ArrayList<>();
        dates.add(record.getTimestamp());
        prices.add(record.getData().getPrice());
        max_prices.add(record.getData().getMax_pr());
        min_prices.add(record.getData().getMin_pr());

        MetaData metaData=record.getMetaData();
        String stateName=metaData.state();
        String districtName=metaData.district();
        String marketName=metaData.market();
        String commodityName=metaData.commodity();
        String varietyName=metaData.variety();
        QueryResult qr=new QueryResult(dates,stateName,districtName, marketName, commodityName, varietyName,min_prices,max_prices,prices);
        return qr;
    }
    @GetMapping("/refresh")
    public ResponseEntity<Records> getDataRef() throws ParseException {
        ResponseEntity<Records> records = new ResponseEntity<>(serviceLayer.consumeAPIRef(),HttpStatus.OK);
        return records;
    }

}
