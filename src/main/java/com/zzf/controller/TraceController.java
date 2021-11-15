package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Trace;
import com.zzf.service.TraceService;
import com.zzf.util.DateUtil;
import com.zzf.util.HttpUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PageVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TRACE(Trace)表控制层
 *
 * @author makejava
 * @since 2021-06-25 16:22:10
 */
@RestController
@Slf4j
@RequestMapping("trace")
public class TraceController {
    /**
     * 服务对象
     */
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private TraceService traceService;


    @ApiOperation("ip查询")
    @GetMapping("ip")
    @IgnoreAuth
    public Object selectOne(HttpServletRequest request) {
        return ResultUtil.success(HttpUtil.getIp());
    }

    @GetMapping("list")
    public Object list(PageVO pageVO) {
        Query query = new Query();
        long count = mongoTemplate.count(query, Trace.class, "logs");
        query.limit(pageVO.getPageSize());
        query.skip((long) (pageVO.getCurrent() - 1) * pageVO.getPageSize());
        query.with(Sort.by("time").descending());
        Map<Object, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("list", mongoTemplate.find(query, Trace.class, "logs"));
        return ResultUtil.success(map);
    }

    @GetMapping("getPerformance")
    public Object getPerformance() {
        AggregationOperation group = Aggregation.group("name").max("value").as("max").min("value").as("min").avg("value").as("avg");
        Aggregation aggregation = Aggregation.newAggregation(group);
        return ResultUtil.success(mongoTemplate.aggregate(aggregation, "logs", Map.class).getMappedResults());
    }

    @GetMapping("uv")
    @IgnoreAuth
    public Object getUv() {
        Criteria criteria = Criteria.where("time").gte(DateUtil.getStartTime(0)).lte(DateUtil.getEndTime(0));
        Criteria.where("name").is("Next.js-hydration");
        AggregationOperation match1 = Aggregation.match(criteria);
        AggregationOperation match2 = Aggregation.match(Criteria.where("name").is("Next.js-hydration"));
        AggregationOperation group = Aggregation.group("visitorId").count().as("visitorIdCount");
        AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "visitorIdCount");
        Aggregation aggregation = Aggregation.newAggregation(match1,match2, group, sort);
        return ResultUtil.success(mongoTemplate.aggregate(aggregation, "logs", Map.class).getMappedResults().size());
    }

    @GetMapping("remove")
    @IgnoreAuth
    public Object remove() {
        return ResultUtil.success(traceService.removeExpiredTrace());
    }

    @PostMapping("save")
    @IgnoreAuth
    public Object save(@RequestBody Trace trace) {
        trace.setIp(HttpUtil.getIp());
        trace.setTime(new Date());
        return ResultUtil.success(mongoTemplate.insert(trace, "logs"));
    }
}
