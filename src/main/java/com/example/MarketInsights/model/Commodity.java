package com.example.MarketInsights.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document
public class Commodity {
    @Id
    private String id;
    private String state;
    private String district;
    private String commodity;
    private String arrival_date;
    private String variety;
    private String min_price;
    private String max_price;
    private String modal_price;

    public Commodity(String id, String state, String district, String commodity, String arrival_date, String variety, String min_price, String max_price, String modal_price) {
        this.id = id;
        this.state = state;
        this.district = district;
        this.commodity = commodity;
        this.arrival_date = arrival_date;
        this.variety = variety;
        this.min_price = min_price;
        this.max_price = max_price;
        this.modal_price = modal_price;
    }
}
