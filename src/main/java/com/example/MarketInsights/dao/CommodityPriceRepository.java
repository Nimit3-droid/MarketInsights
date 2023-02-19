package com.example.MarketInsights.dao;


import com.example.MarketInsights.model.CommodityPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommodityPriceRepository extends MongoRepository<CommodityPrice,String> {
}
