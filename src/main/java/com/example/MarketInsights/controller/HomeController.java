package com.example.MarketInsights.controller;

import com.example.MarketInsights.VO.Records;
import com.example.MarketInsights.dao.CommodityRepository;
import com.example.MarketInsights.model.Commodity;
import com.example.MarketInsights.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

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
    private CommodityRepository commodityRepository;

    /**
     *
     * @param limit
     * @return
     */
    @GetMapping("/all/{limit}")
    public ResponseEntity<Records> getDataLimit(@PathVariable String limit){
        ResponseEntity<Records> records = new ResponseEntity<Records>(serviceLayer.consumeAPILimit(limit), HttpStatus.OK);

        return records;
    }

    /**
     *
     * @param stateName
     * @return
     */
    @GetMapping("/state/{stateName}")
    public ResponseEntity<Records> getDataState(@PathVariable String stateName){
        ResponseEntity<Records> records = new ResponseEntity<Records>(serviceLayer.consumeAPIState(stateName), HttpStatus.OK);
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
    @GetMapping("/getItem/{state}/{district}/{market}/{commodity}/{variety}")
    public ResponseEntity<Records> getData(@PathVariable String state,@PathVariable String district,@PathVariable String market, @PathVariable String  commodity, @PathVariable String variety){
        ResponseEntity<Records> records = new ResponseEntity<Records>(serviceLayer.consumeAPIMandi(state,district,market,commodity,variety), HttpStatus.OK);
        return records;
    }

    /**
     *
     * @return
     * @throws ParseException
     */
    @GetMapping("/refresh")
    public String getDataRef() throws ParseException {
        ResponseEntity<Records> records = new ResponseEntity<>(serviceLayer.consumeAPIRef(),HttpStatus.OK);
        return "Commodity Refreshed";
    }

    /**
     *
     * @return
     */
    @GetMapping("/getAllCommodity")
    public List<Commodity> getCommodity(){
        return commodityRepository.findAll();
    }

    /**
     *
     * @param state
     * @return
     */
    @GetMapping("/getCommodity/{state}")
    public List<Commodity> getCommodityState(@PathVariable String state){
        return commodityRepository.getCommodityByState(state);
    }

    /**
     *
     * @param state
     * @param district
     * @return
     */
    @GetMapping("/getCommodity/{state}/{district}")
    public List<Commodity> getCommodityDistrict(@PathVariable String state,@PathVariable String district){
        return commodityRepository.getCommodityByDistrict(state,district);
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @return
     */
    @GetMapping("/getCommodity/{state}/{district}/{market}")
    public List<Commodity> getCommodityMarket(@PathVariable String state,@PathVariable String district, @PathVariable String market){
        return commodityRepository.getCommodityByMarket(state,district,market);
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @return
     */
    @GetMapping("/getCommodity/{state}/{district}/{market}/{commodity}")
    public List<Commodity> getCommodityName(@PathVariable String state,@PathVariable String district, @PathVariable String market,@PathVariable String commodity){
        return commodityRepository.getCommodityByName(state,district,market,commodity);
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
    @GetMapping("/getCommodity/{state}/{district}/{market}/{commodity}/{variety}")
    public Commodity getCommodityVarietyName(@PathVariable String state,@PathVariable String district, @PathVariable String market,@PathVariable String commodity,@PathVariable String variety){
        return commodityRepository.getCommodityByVariety(state,district,market,commodity,variety);
    }


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/getCommodityById/{id}")
    public Commodity getCommodityByID(@PathVariable String id){
        return commodityRepository.findById(id).get();

    }


    /**
     *
     * @return
     */
    @GetMapping("/getStates/")
    public Set<String> getStates(){
        return serviceLayer.getStatesList();
    }

    /**
     *
     * @param state
     * @return
     */
    @GetMapping("/getDistricts/{state}")
    public Set<String> getDistricts(@PathVariable String state){
        return serviceLayer.getDistrictList(state);
    }

    /**
     *
     * @param state
     * @param district
     * @return
     */
    @GetMapping("/getMarkets/{state}/{district}")
    public Set<String> getMarkets(@PathVariable String state,@PathVariable String district){
        return serviceLayer.getMarketList(state,district);
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @return
     */
    @GetMapping("/getCommodities/{state}/{district}/{market}")
    public Set<String> getCommodities(@PathVariable String state,@PathVariable String district,@PathVariable String market){
        return serviceLayer.getCommodityList(state,district,market);
    }

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @return
     */
    @GetMapping("/getVarieties/{state}/{district}/{market}/{commodity}")
    public Set<String> getVarieties(@PathVariable String state,@PathVariable String district,@PathVariable String market,@PathVariable String commodity){
        return serviceLayer.getVarietyList(state,district,market,commodity);
    }


}
