package com.zzf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.Trace;
import com.zzf.mapper.TraceMapper;
import com.zzf.service.TraceService;
import com.zzf.util.DateUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PerformanceVO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author cc
 */
@Service
public class TraceServiceImpl extends ServiceImpl<TraceMapper, Trace>
        implements TraceService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Object removeExpiredTrace() {
        Query query = new Query();
        Criteria criteria = Criteria.where("time").lt(DateUtil.getDeleteDate(new Date(), 30));
        query.addCriteria(criteria);
        return mongoTemplate.remove(query, Trace.class, "logs");
    }

    @Override
    public Object getPerformance() {
        AggregationOperation group = Aggregation.group("name").max("value").as("max").min("value").as("min").avg("value").as("avg");
        Aggregation aggregation = Aggregation.newAggregation(group);
        return mongoTemplate.aggregate(aggregation, "logs", PerformanceVO.class).getMappedResults().stream().sorted((Comparator.comparing(PerformanceVO::getId))).collect(Collectors.toList());
    }

    @Override
    public Object getUserCount() {
        Criteria criteria = Criteria.where("name").is("Next.js-hydration");
        Query query = new Query();
        query.addCriteria(criteria);
        long allPageView = mongoTemplate.count(query, Trace.class, "logs");
        query.addCriteria(Criteria.where("time").gte(DateUtil.getStartTime()).lte(DateUtil.getEndTime()));
        long todayPageView = mongoTemplate.count(query, Trace.class, "logs");
        AggregationOperation match2 = Aggregation.match(criteria);
        AggregationOperation group = Aggregation.group("visitorId").count().as("visitorIdCount");
        Aggregation aggregation1 = Aggregation.newAggregation(match2, group);
        long allUserView = mongoTemplate.aggregate(aggregation1, "logs", Map.class).getMappedResults().size();
        AggregationOperation match1 = Aggregation.match(Criteria.where("time").gte(DateUtil.getStartTime()).lte(DateUtil.getEndTime()));
        Aggregation aggregation2 = Aggregation.newAggregation(match1, match2, group);
        long todayUserView = mongoTemplate.aggregate(aggregation2, "logs", Map.class).getMappedResults().size();
        Map<String, Object> map = new HashMap<>();
        map.put("allPageView", allPageView);
        map.put("todayPageView", todayPageView);
        map.put("allUniqueVisitor", allUserView);
        map.put("todayUniqueVisitor", todayUserView);
        return map;
    }
}




