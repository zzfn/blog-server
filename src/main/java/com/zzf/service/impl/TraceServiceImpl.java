package com.zzf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.Trace;
import com.zzf.mapper.TraceMapper;
import com.zzf.service.TraceService;
import com.zzf.util.DateUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PerformanceVO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
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
        Criteria criteria = Criteria.where("time").lt(DateUtil.getDeleteDate(new Date(),30));
        query.addCriteria(criteria);
        return mongoTemplate.remove(query, Trace.class, "logs");
    }

    @Override
    public Object getPerformance() {
        AggregationOperation group = Aggregation.group("name").max("value").as("max").min("value").as("min").avg("value").as("avg");
        Aggregation aggregation = Aggregation.newAggregation(group);
        return mongoTemplate.aggregate(aggregation, "logs", PerformanceVO.class).getMappedResults().stream().sorted((Comparator.comparing(PerformanceVO::getId))).collect(Collectors.toList());
    }
}




