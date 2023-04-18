package com.example.MarketInsights.dao;



import com.example.MarketInsights.VO.Commodities;
import com.example.MarketInsights.VO.RegionQueries;
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
            {  'metaData.state': :#{#metaData.state()},
                timestamp:          { $gte: ?1, $lt: ?2 }
            }""")
    List<Measurement> findInIntervalTest(MetaData metaData, Instant timeGE, Instant timeLT);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}} }",
            "{$group:{_id:{district:'$metaData.district'}}}",
            "{$sort:{_id:1}}"
    })
    List<RegionQueries> findDistinctDistrictsByState(MetaData metaData);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," + "  'metaData.district': :#{#metaData.district()}} }",
            "{$group:{_id:{market:'$metaData.market'}}}",
            "{$sort:{_id:1}}"
    })
    List<RegionQueries> findDistinctMarketsByDistrict(MetaData metaData);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()} }}",
            "{ $sort: { timestamp: -1 } }",
            "{$group: {_id: '$metaData.market', detail: {$addToSet:{commodity:'$metaData.commodity',variety:'$metaData.variety'}},timestamp: {$first:'$timestamp'}}},",
            "{ $sort: { _id: 1 } }",
            "{ $project: { detail: 1, _id: 1 } }",
    })
    List<Commodities> findAllByDistrict(MetaData metaData);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()} } }",
            "{$group:{_id:{commodity:'$metaData.commodity'}}}",
            "{$sort:{_id:1}}"
    })
    List<RegionQueries> findDistinctCommodityByMarket(MetaData metaData);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()},"+
                    "   'metaData.commodity': :#{#metaData.commodity()}} }",
            "{$group:{_id:{variety:'$metaData.variety'}}}",
            "{$sort:{_id:1}}"
    })
    List<RegionQueries> findDistinctVarietyByCommodity(MetaData metaData);



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
               'metaData.variety': :#{#metaData.variety()},
                timestamp:          { $eq: ?1}
            }""")
    List<Measurement> findByMetaDataTime(MetaData metaData, Instant timeGE);

    @Query("""
            {  'metaData.state': :#{#metaData.state()}
               'metaData.district': :#{#metaData.district()}
               'metaData.market': :#{#metaData.market()}
               'metaData.commodity': :#{#metaData.commodity()}
               'metaData.variety': :#{#metaData.variety()}
            }""")
    List<Measurement> findAll(MetaData metaData);
    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()},"+
                    "     timestamp:          { $eq: ?1}} }",
            "{ $sort: { 'data.price': 1 } }"
    })
    List<Measurement> findMarket(MetaData metaData,Instant timeGT);
    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()},"+
                    "     timestamp:          { $eq: ?1}} }",
            "{ $sort: { 'data.price': 1 } }"
    })
    List<Measurement> findDistrictMarket(MetaData metaData,Instant timeGT);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()}    } }",
            "{ $sort: { timestamp: -1 } }",
            "{ $limit: 1 }"})
    Measurement findLast(MetaData metaData);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()}    } }",
            "{ $sort: { timestamp: -1 } }",
            "{ $limit: ?1 }"})
    List<Measurement> findForDays(MetaData metaData,int days);


    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()}," +
                    "     timestamp:          { $gte: ?1, $lt: ?2 }      } } }",
            "{ $group: { _id:0,average: { $avg: '$data.price'}} }"
    })
    List<BucketDataDto> findAvgInInterval(MetaData metaData,Instant timeGE,Instant timeLT);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()}    } }",
            "{ $group: { _id:0,average: { $avg: '$data.price'}} }",
    })
    List<BucketDataDto> findAvg(MetaData metaData);

    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()},"+
            "     timestamp:          { $gte: ?1, $lt: ?2 }      } } }",
            "{ $group: { _id:0,minPrice:{$min:'$data.price'}}}"
    })
    List<BucketDataDto> findMinInInterval(MetaData metaData,Instant timeGE,Instant timeLT);
    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()}    } }",
            "{ $group: { _id:0,minPrice:{$min:'$data.price'}}}"
    })
    List<BucketDataDto> findMin(MetaData metaData);



    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()},"+
                    "     timestamp:          { $gte: ?1, $lt: ?2 }      } } }",
            "{ $group: { _id:0,maxPrice:{$max:'$data.price'}}}"
    })
    List<BucketDataDto> findMaxInInterval(MetaData metaData,Instant timeGE,Instant timeLT);


    @Aggregation({
            "{ $match: { 'metaData.state': :#{#metaData.state()}," +
                    "    'metaData.district': :#{#metaData.district()}," +
                    "    'metaData.market': :#{#metaData.market()}," +
                    "    'metaData.commodity': :#{#metaData.commodity()}," +
                    "    'metaData.variety': :#{#metaData.variety()}    } }",
            "{ $group: { _id:0,maxPrice:{$max:'$data.price'}}}"
    })
    List<BucketDataDto> findMax(MetaData metaData);

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
