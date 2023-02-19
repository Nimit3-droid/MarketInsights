package com.example.MarketInsights.dao;

import com.example.MarketInsights.model.Commodity;
import com.example.MarketInsights.model.CommodityPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
public interface CommodityRepository extends MongoRepository<Commodity,String> {
    @Query("{state: ?0}")
    List<Commodity> getCommodityByState(String state);
    @Query("{state: ?0, district: ?1}")
    List<Commodity> getCommodityByDistrict(String state, String district);
    @Query("{state: ?0, district: ?1, market: ?2}")
    List<Commodity> getCommodityByMarket(String state, String district, String market);
    @Query("{state: ?0, district: ?1, market: ?2, commodity: ?3}")
    List<Commodity> getCommodityByName(String state, String district, String market, String commodity);
    @Query("{state: ?0, district: ?1, market: ?2, commodity: ?3, variety: ?4}")
    List<Commodity> getCommodityByVariety(String state, String district, String market, String commodity, String variety);

//    @Query("{}")
//    List<CommodityPrice> getCommodityById(String id);
}
