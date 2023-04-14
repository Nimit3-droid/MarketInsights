package com.example.MarketInsights.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryInsights {
    private String name;
    private double value;

    public QueryInsights(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
