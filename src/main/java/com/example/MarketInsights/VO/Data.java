package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private String state;
    private String district;
    private String market;
    private String commodity;
    private String variety;
    private String arrival_date;
    private String min_price;
    private String max_price;
    private String modal_price;


}