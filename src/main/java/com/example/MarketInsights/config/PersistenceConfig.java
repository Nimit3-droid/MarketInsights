package com.example.MarketInsights.config;

import com.example.MarketInsights.model.Measurement;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;


@RequiredArgsConstructor
@Configuration
public class PersistenceConfig {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        ensureCollectionExists(Measurement.class);

        ensureMetaDataTimestampIndex(Measurement.class);
    }

    private void ensureCollectionExists(Class<?> collectionClass) {
        if (!mongoTemplate.collectionExists(collectionClass)) {
            mongoTemplate.createCollection(collectionClass);
        }
    }

    private void ensureMetaDataTimestampIndex(Class<?> collectionClass) {
        mongoTemplate.indexOps(collectionClass).ensureIndex(
                new CompoundIndexDefinition(new Document()
                        .append("metaData.state", 1)
                        .append("metaData.district", 1)
                        .append("timestamp", 1)));
    }

}
