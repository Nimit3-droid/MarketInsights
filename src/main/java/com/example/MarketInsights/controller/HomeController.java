package com.example.MarketInsights.controller;


import com.example.MarketInsights.VO.QueryResult;
import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.dao.MeasurementRepository;
import com.example.MarketInsights.dao.StateRepository;
import com.example.MarketInsights.dto.BucketDataDto;
import com.example.MarketInsights.model.Measurement;
import com.example.MarketInsights.model.MetaData;
import com.example.MarketInsights.model.State;
import com.example.MarketInsights.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StateRepository stateRepository;
    @GetMapping("/")
    public String getDataLimit(){
        return "Home";
    }

    @GetMapping("/save")
    public String testAPICont(){
        serviceLayer.testAPI();
        return "Home";
    }

    @GetMapping("/getInterval/{startDate}/{endDate}/{state}/{district}/{market}/{commodity}/{variety}")
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

    @GetMapping("/getAll/{state}/{district}/{market}/{commodity}/{variety}")
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
        int days =1;
        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getDaysMeasurement(state,district,market,commodity,variety,days), HttpStatus.OK).getBody();
        if(records.size()==0){
            return new QueryResult();
        }
        System.out.println(state);
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

    @GetMapping("/getLastD/{state}/{district}/{market}/{commodity}/{variety}/{days}")
    public QueryResult queryAllPriceList(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety,@PathVariable int days){

        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getDaysMeasurement(state,district,market,commodity,variety,days), HttpStatus.OK).getBody();
        if(records.size()==0){
            return new QueryResult();
        }
        System.out.println(state);
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

    @GetMapping("/getAvg/{state}/{district}/{market}/{commodity}/{variety}")
    public List<BucketDataDto> queryAvg(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getAvgMeasurement(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }


    @GetMapping("/getAvgInterval/{startDate}/{endDate}/{state}/{district}/{market}/{commodity}/{variety}")
    public List<BucketDataDto> getAvgInInterval(@PathVariable String startDate,@PathVariable String endDate,@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety) {
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getAvgMeasurementInInterval(startDate,endDate,state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    @GetMapping("/getMin/{state}/{district}/{market}/{commodity}/{variety}")
    public List<BucketDataDto> queryMin(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMinMeasurement(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    @GetMapping("/getMinInterval/{startDate}/{endDate}/{state}/{district}/{market}/{commodity}/{variety}")
    public List<BucketDataDto> getMinInInterval(@PathVariable String startDate,@PathVariable String endDate,@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety) {
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMinMeasurementInInterval(startDate,endDate,state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    @GetMapping("/getMax/{state}/{district}/{market}/{commodity}/{variety}")
    public List<BucketDataDto> queryMax(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMaxMeasurement(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }
    @GetMapping("/getMaxInterval/{startDate}/{endDate}/{state}/{district}/{market}/{commodity}/{variety}")
    public List<BucketDataDto> getMaxInInterval(@PathVariable String startDate,@PathVariable String endDate,@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety) {
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMaxMeasurementInInterval(startDate,endDate,state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    @PostMapping("/addState")
    public String addState(@RequestBody String state) throws ParseException {
        try{
            stateRepository.insert(new State(state));
        }
        catch (Exception exception) {
            return state + " Already Exists";
        }
        return state + " added Successfully";
    }
    @GetMapping("/refresh")
    public ArrayList<ResponseEntity<Records>> getDataRef() throws ParseException {
        List<State> states=stateRepository.findAll();
        ArrayList<ResponseEntity<Records>> response=new ArrayList<>();
        for(State state : states){
            ResponseEntity<Records> records = new ResponseEntity<>(serviceLayer.consumeAPIRef(state.getState()),HttpStatus.OK);
            response.add(records);
        }
        return response;
    }

}
