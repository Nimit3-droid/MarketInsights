package com.example.MarketInsights.controller;


import com.example.MarketInsights.VO.QueryInsights;
import com.example.MarketInsights.VO.QueryResult;
import com.example.MarketInsights.VO.Records;
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
import java.util.*;

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
    public QueryResult queryAll(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
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

    @GetMapping("/getBestMarketPrice/{startDate}/{state}/{district}/{commodity}/{variety}")
    public List<QueryInsights> getBestMarketAndPrice(@PathVariable String startDate,@PathVariable String state,@PathVariable String district,@PathVariable String commodity,@PathVariable String variety){
        List<QueryInsights> result=new ResponseEntity<>(serviceLayer.getBestMarketPlace(startDate,state,district,commodity,variety),HttpStatus.OK).getBody();
        return result;
    }

    @GetMapping("/getBestDistrictMarketPrice/{startDate}/{state}/{commodity}/{variety}")
    public List<QueryInsights> getBestDistrictMarketAndPrice(@PathVariable String startDate,@PathVariable String state,@PathVariable String commodity,@PathVariable String variety){
        List<QueryInsights> result=new ResponseEntity<>(serviceLayer.getBestDistrictMarketPlace(startDate,state,commodity,variety),HttpStatus.OK).getBody();
        return result;
    }


    @GetMapping("/getBestTimeToSell/{state}/{district}/{market}/{commodity}/{variety}")
    public HashMap<String,Double> getBestTimeToSell(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getAllPrice(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        HashMap<String,Double> months=new HashMap<>();
        HashMap<String,Double> frequency=new HashMap<>();
        if(records.size()==0){
            return months;
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

            String date=measure.getTimestamp().toString();
            String month=date.substring(5,7);
            double price = measure.getData().getPrice();

            if(!months.containsKey(month)){
                months.putIfAbsent(month,price);
                frequency.putIfAbsent(month,1.0);
            }else{
                months.put(month, months.get(months)+price);
                frequency.put(month,frequency.get(month)+1.0);
            }
        }
        for(Map.Entry<String,Double> entry:frequency.entrySet()){
            String month=entry.getKey();
            months.put(month, months.get(month)/frequency.get(month));
        }
//        System.out.println(months);
        MetaData metaData=records.get(0).getMetaData();
        String stateName=metaData.state();
        String districtName=metaData.district();
        String marketName=metaData.market();
        String commodityName=metaData.commodity();
        String varietyName=metaData.variety();
        QueryResult qr=new QueryResult(dates,stateName,districtName, marketName, commodityName, varietyName,min_prices,max_prices,prices);
        return months;
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

    @GetMapping("/getDistricts/{state}")
    public List<Measurement> getDistricts(@PathVariable String state){
        List<Measurement> result=new ResponseEntity<>(serviceLayer.getDistinctDistricts(state),HttpStatus.OK).getBody();
        return result;
    }
    @GetMapping("/getMarkets/{state}/{district}")
    public List<Measurement> getMarkets(@PathVariable String state,@PathVariable String district){
        List<Measurement> result=new ResponseEntity<>(serviceLayer.getDistinctMarkets(state,district),HttpStatus.OK).getBody();
        return result;
    }
    @GetMapping("/getCommodity/{state}/{district}/{market}")
    public List<Measurement> getCommodity(@PathVariable String state,@PathVariable String district,@PathVariable String market){
        List<Measurement> result=new ResponseEntity<>(serviceLayer.getDistinctCommodity(state,district,market),HttpStatus.OK).getBody();
        return result;
    }
    @GetMapping("/getVariety/{state}/{district}/{market}/{commodity}")
    public List<Measurement> getCommodity(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity){
        List<Measurement> result=new ResponseEntity<>(serviceLayer.getDistinctVariety(state,district,market,commodity),HttpStatus.OK).getBody();
        return result;
    }

    //test
    @GetMapping("/testQuery/{startDate}/{endDate}/{state}")
    public QueryResult testQuery(@PathVariable String startDate,@PathVariable String endDate,@PathVariable String state){
        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getIntervals(startDate,endDate,state), HttpStatus.OK).getBody();
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


}
