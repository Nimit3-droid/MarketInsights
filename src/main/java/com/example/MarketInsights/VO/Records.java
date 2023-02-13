package com.example.MarketInsights.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Records {
    private String created;
    private String updated;

    private List<Data> records;

    public List<Data> getRecords() {
        return records;
    }

    public void setRecords(List<Data> records) {
        this.records = records;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
