package com.example.MarketInsights.dao;

import com.example.MarketInsights.model.Commodity;
import com.example.MarketInsights.model.CommodityPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
public interface CommodityRepository extends MongoRepository<Commodity,String> {

    /**
     *
     * @param state
     * @return
     */
    @Query("{state: ?0}")
    List<Commodity> getCommodityByState(String state);

    /**
     *
     * @param state
     * @param district
     * @return
     */
    @Query("{state: ?0, district: ?1}")
    List<Commodity> getCommodityByDistrict(String state, String district);

    /**
     *
     * @param state
     * @param district
     * @param market
     * @return
     */
    @Query("{state: ?0, district: ?1, market: ?2}")
    List<Commodity> getCommodityByMarket(String state, String district, String market);

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @return
     */
    @Query("{state: ?0, district: ?1, market: ?2, commodity: ?3}")
    List<Commodity> getCommodityByName(String state, String district, String market, String commodity);

    /**
     *
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @return
     */
    @Query("{state: ?0, district: ?1, market: ?2, commodity: ?3, variety: ?4}")
    Commodity getCommodityByVariety(String state, String district, String market, String commodity, String variety);

}
