package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Records {
    private String created;
    private String updated;
    private int total;

    private List<Data> records;


}