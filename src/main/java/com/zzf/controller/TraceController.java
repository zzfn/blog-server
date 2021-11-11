package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Trace;
import com.zzf.service.TraceService;
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

    @GetMapping("fcp")
    @IgnoreAuth
    public Object fcp() {
        Query query = new Query();
        AggregationOperation group = Aggregation.group("name").max("value").as("nameMax");
        Aggregation aggregation = Aggregation.newAggregation(group);
        return ResultUtil.success(mongoTemplate.aggregate(aggregation, "logs", Map.class));
    }

    @GetMapping("uv")
    @IgnoreAuth
    public Object getUv() {
        Criteria criteria = Criteria.where("time").gte(getStartTime()).lte(getEndTime());
        AggregationOperation match = Aggregation.match(criteria);
        AggregationOperation group = Aggregation.group("visitorId").count().as("visitorIdCount");
        AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "visitorIdCount");
        Aggregation aggregation = Aggregation.newAggregation(match, group, sort);
        log.error("{}",aggregation);
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "logs", Map.class);
        return ResultUtil.success(result.getMappedResults());
    }

    @GetMapping("remove")
    @IgnoreAuth
    public Object remove() {
        return ResultUtil.success(traceService.removeExpiredTrace());
    }

    private static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    private static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    @PostMapping("save")
    @IgnoreAuth
    public Object save(@RequestBody Trace trace) {
        trace.setIp(HttpUtil.getIp());
        trace.setTime(new Date());
        return ResultUtil.success(mongoTemplate.insert(trace, "logs"));
    }
}
