package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


}
