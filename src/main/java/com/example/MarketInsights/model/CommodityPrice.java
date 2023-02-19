package com.example.MarketInsights.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class CommodityPrice {

    public CommodityPrice(String id, Date date, int price, int max_price, int min_price) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.max_price = max_price;
        this.min_price = min_price;
    }

    @Id
    private String id;
    private Date date;
    private int price;
    private  int max_price;
    private int min_price;
}
