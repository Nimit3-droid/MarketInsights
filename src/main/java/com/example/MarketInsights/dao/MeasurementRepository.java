package com.example.MarketInsights.dao;



import com.example.MarketInsights.dto.BucketDataDto;
import com.example.MarketInsights.model.Measurement;
import com.example.MarketInsights.model.MetaData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, String> {


    @Query("""
            {  'metaData.state': :#{#metaData.state()}
               'metaData.district': :#{#metaData.district()}
               'metaData.market': :#{#metaData.market()}
               'metaData.commodity': :#{#metaData.commodity()}
               'metaData.variety': :#{#metaData.variety()},
                timestamp:          { $gte: ?1, $lt: ?2 }
            }""")
    List<Measurement> findInInterval(MetaData metaData, Instant timeGE, Instant timeLT);

    @Query("""
            {  'metaData.state': :#{#metaData.state()}
               'metaData.district': :#{#metaData.district()}
               'metaData.market': :#{#metaData.market()}
               'metaData.commodity': :#{#metaData.commodity()}
               'metaData.variety': :#{#metaData.variety()}
            }""")
    List<Measurement> findAllData(MetaData metaData);

//    @Aggregation({
//            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
//                    "    'metaData.district': :#{#metaData.district()}   } }",
//            "{ $sort: { timestamp: -1 } }",
//            "{ $limit: 1 }"})
//    Measurement findLast(MetaData metaData);


//    @Aggregation({
//            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
//                    "    'metaData.district': :#{#metaData.district()}," +
//                    "     timestamp:          { $gte: ?1, $lt: ?2 }      } } }",
//            "{ $bucket: { groupBy: '$timestamp', boundaries: ?3, output: {" +
//                    "     average: { $avg:  '$value' }," +
//                    "     last:    { $last: '$value' } " +
//                    " } } }",
//            "{ $project: { startDate: '$_id', average: 1, last: 1, _id: 0 } }",
//            "{ $sort: { startDate : 1 } }"
//    })
//    List<BucketDataDto> findBuckets(MetaData metaData, Instant timeGE, Instant timeLT, List<Instant> bucketBoundaries);

}
