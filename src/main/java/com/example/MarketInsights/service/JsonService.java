package com.example.MarketInsights.service;

import com.example.MarketInsights.VO.PriceContainer;
import com.example.MarketInsights.dao.MeasurementRepository;
import com.example.MarketInsights.model.Measurement;
import com.example.MarketInsights.model.MetaData;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonService {
    private final RestTemplate restTemplate;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    public JsonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     *
     * @param rootNode
     * @return
     */
    public String addJSON_toDB(JsonNode rootNode){
        int insertionsCount =0,totalInsertions=0;

        for (JsonNode root : rootNode) {
            List<Measurement> measurements = new ArrayList<>();
            String state = root.path("state").asText();
            String district = root.path("district").asText();
            String market = root.path("market").asText();
            String commodity = root.path("commodity").asText();
            String variety = root.path("variety").asText();
            MetaData metaData = new MetaData(state, district, market, commodity, variety);
            JsonNode modal_price = root.path("modal_price");
            if (modal_price.isArray()) {
                for (JsonNode node : modal_price) {
                    totalInsertions++;
                    double price = node.path("price").asDouble();
                    double min_price = node.path("min_price").asDouble();
                    double max_price = node.path("max_price").asDouble();
                    String id = node.path("_id").asText();
                    String date = id.substring(id.length() - 10);
//                    System.out.println(insertionsCount+" state: "+state+" " +date);
                    String year = date.substring(6), month = date.substring(3, 5), day = date.substring(0, 2);
                    Instant timestamp = Instant.parse(year + "-" + month + "-" + day + "T18:00:00.00Z");
                    List<Measurement> list = measurementRepository.findByMetaDataTime(metaData, timestamp);
                    if (list.size() == 0) {
                        insertionsCount++;
                        PriceContainer data = new PriceContainer(price, min_price, max_price);
                        measurements.add(new Measurement(timestamp, metaData, data));
                    }
                }
            }

            measurementRepository.saveAll(measurements);
        }
        return insertionsCount+"/"+totalInsertions;
    }
}
