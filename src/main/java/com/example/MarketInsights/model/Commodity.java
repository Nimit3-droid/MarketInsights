package com.example.MarketInsights.model;


import com.example.MarketInsights.dao.CommodityPriceRepository;
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
    private String market;
    private String commodity;
    private String variety;
    private ArrayList<CommodityPrice> modal_price;


    /**
     *
     * @param id
     * @param state
     * @param district
     * @param market
     * @param commodity
     * @param variety
     * @param modal_price
     */
    public Commodity(String id, String state, String district, String market, String commodity, String variety, ArrayList<CommodityPrice> modal_price) {
        this.id = id;
        this.state = state;
        this.district = district;
        this.market = market;
        this.commodity = commodity;
        this.variety = variety;
        this.modal_price = modal_price;
    }



    public ArrayList<CommodityPrice> getModal_price() {
        return modal_price;
    }

    /**
     *
     * @param modal_price
     */
    public void setModal_price(ArrayList<CommodityPrice> modal_price) {
        this.modal_price = modal_price;
    }
}
