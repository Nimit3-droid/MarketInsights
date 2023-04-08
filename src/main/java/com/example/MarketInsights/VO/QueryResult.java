package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResult {
    private List<Instant> instant;
    private String state;
    private String district;
    private String market;
    private String commodity;
    private String variety;
    private List<Double> min_price;
    private List<Double> max_price;
    private List<Double> price;

    public QueryResult(List<Instant> instant, String state, String district, String market, String commodity, String variety, List<Double> min_price, List<Double> max_price, List<Double> price) {
        this.instant = instant;
        this.state = state;
        this.district = district;
        this.market = market;
        this.commodity = commodity;
        this.variety = variety;
        this.min_price = min_price;
        this.max_price = max_price;
        this.price = price;
    }
    public QueryResult(){}
}
