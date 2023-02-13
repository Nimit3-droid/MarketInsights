package com.example.MarketInsights.dao;

import com.example.MarketInsights.model.Commodity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommodityRepository extends MongoRepository<Commodity,String> {
}
