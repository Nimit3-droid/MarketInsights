package com.example.MarketInsights.controller;


import com.example.MarketInsights.VO.*;
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
@CrossOrigin(origins ="${my.property}")
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


    //2022-11-02 [---)
    /**
     *
     * @param startDate
     * @param endDate
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getInterval")
    public QueryResult queryInRange(@RequestParam String startDate,@RequestParam String endDate,@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety){
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

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getAll")
    public QueryResult queryAll(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety){
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

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getLast")
    public QueryResult queryLast(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety){
        int days =1;
        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getDaysMeasurement(state,district,market,commodity,variety,days), HttpStatus.OK).getBody();
        if(records.size()==0){
            return new QueryResult();
        }
//        System.out.println(state);
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

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @param days
     * @return
     */
    @GetMapping("/getLastD")
    public QueryResult queryAllPriceList(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety,@RequestParam int days){

        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getDaysMeasurement(state,district,market,commodity,variety,days), HttpStatus.OK).getBody();
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

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getAvg")
    public List<BucketDataDto> queryAvg(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety){
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getAvgMeasurement(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getAvgInterval")
    public List<BucketDataDto> getAvgInInterval(@RequestParam String startDate,@RequestParam String endDate,@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety) {
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getAvgMeasurementInInterval(startDate,endDate,state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getMin")
    public List<BucketDataDto> queryMin(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety){
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMinMeasurement(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getMinInterval")
    public List<BucketDataDto> getMinInInterval(@RequestParam String startDate,@RequestParam String endDate,@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety) {
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMinMeasurementInInterval(startDate,endDate,state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getMax")
    public List<BucketDataDto> queryMax(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety){
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMaxMeasurement(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getMaxInterval")
    public List<BucketDataDto> getMaxInInterval(@RequestParam String startDate,@RequestParam String endDate,@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety) {
        List<BucketDataDto> records = new ResponseEntity<>(serviceLayer.getMaxMeasurementInInterval(startDate,endDate,state,district,market,commodity,variety), HttpStatus.OK).getBody();
        return records;
    }

    /**
     *
     * @param state
     * @return
     * @throws ParseException
     */
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

    /**
     *
     * @param startDate
     * @param state
     * @param district
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getBestMarketPrice")
    public List<QueryInsights> getBestMarketAndPrice(@RequestParam String startDate,@RequestParam String state,@RequestParam String district,@RequestParam String commodity,@RequestParam String variety){
        List<QueryInsights> result=new ResponseEntity<>(serviceLayer.getBestMarketPlace(startDate,state,district,commodity,variety),HttpStatus.OK).getBody();
        return result;
    }

    /**
     *
     * @param startDate
     * @param state
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getBestDistrictMarketPrice")
    public List<QueryInsights> getBestDistrictMarketAndPrice(@RequestParam String startDate,@RequestParam String state,@RequestParam String commodity,@RequestParam String variety){
        List<QueryInsights> result=new ResponseEntity<>(serviceLayer.getBestDistrictMarketPlace(startDate,state,commodity,variety),HttpStatus.OK).getBody();
        return result;
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @GetMapping("/getBestTimeToSell")
    public HashMap<String,Double> getBestTimeToSell(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity,@RequestParam String variety){
        List<Measurement> records = new ResponseEntity<List<Measurement>>(serviceLayer.getAllPrice(state,district,market,commodity,variety), HttpStatus.OK).getBody();
        HashMap<String,Double> months=new HashMap<>();
        HashMap<String,Double> frequency=new HashMap<>();
        months.put("10",0.0);
        frequency.put("10",0.0);
        for(int j=0;j<2;j++){
            for(int i=1;i<=9;i++){
                String mth=j+""+i;
                if(months.size()<12){
                    months.put(mth,0.0);
                    frequency.put(mth,0.0);
                }
            }
        }

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
            months.put(month, months.get(month)+price);
            frequency.put(month,frequency.get(month)+1.0);

        }
        for(Map.Entry<String,Double> entry:frequency.entrySet()){
            String month=entry.getKey();
            if(frequency.get(month)!=0.0)months.put(month, months.get(month)/frequency.get(month));
        }
        return months;
    }

    /**
     *
     * @return
     * @throws ParseException
     */
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

    /**
     *
     * @return
     */
    @GetMapping("/getAllStates")
    public List<State> getAllStates(){
        List<State> states=stateRepository.findAll();
        return states;
    }

    /**
     *
     * @param state
     * @return
     */
    @GetMapping("/getDistricts")
    public List<RegionQueries> getDistricts(@RequestParam String state){
        List<RegionQueries> result=new ResponseEntity<>(serviceLayer.getDistinctDistricts(state),HttpStatus.OK).getBody();
        return result;
    }

    /**
     *
     * @param state
     * @param district
     * @return
     */
    @GetMapping("/getMarkets")
    public List<RegionQueries> getMarkets(@RequestParam String state, @RequestParam String district){
        List<RegionQueries> result=new ResponseEntity<>(serviceLayer.getDistinctMarkets(state,district),HttpStatus.OK).getBody();
        return result;
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @return
     */
    @GetMapping("/getCommodities")
    public List<RegionQueries> getCommodity(@RequestParam String state,@RequestParam String district,@RequestParam String market){
        List<RegionQueries> result=new ResponseEntity<>(serviceLayer.getDistinctCommodity(state,district,market),HttpStatus.OK).getBody();
        return result;
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @return
     */
    @GetMapping("/getVarieties")
    public List<RegionQueries> getCommodity(@RequestParam String state,@RequestParam String district,@RequestParam String market,@RequestParam String commodity){
        List<RegionQueries> result=new ResponseEntity<>(serviceLayer.getDistinctVariety(state,district,market,commodity),HttpStatus.OK).getBody();
        return result;
    }

    /**
     *
     * @param state
     * @param district
     * @return
     */
    @GetMapping("/getAllCommodities")
    public List<Commodities> getAllCommodities(@RequestParam String state,@RequestParam String district){
        List<Commodities> result=new ResponseEntity<>(serviceLayer.getAllDataInDistrict(state,district),HttpStatus.OK).getBody();
        return result;
    }



}
