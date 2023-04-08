package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceContainer {
    private double price,min_pr,max_pr;

    public PriceContainer(double price, double min_pr, double max_pr) {
        this.price = price;
        this.min_pr = min_pr;
        this.max_pr = max_pr;
    }
}
