package com.example.MarketInsights.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class State {

    @Id
    @Field("state")
    private String state;

    public State(String state) {
        this.state = state;
    }
}
