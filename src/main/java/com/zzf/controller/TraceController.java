package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Trace;
import com.zzf.util.IpUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzf
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

    @PostMapping("save")
    @IgnoreAuth
    public Object save(@RequestBody Trace trace) {
        trace.setIp(IpUtil.getIp());
        trace.setTime(new Date());
        return ResultUtil.success(mongoTemplate.insert(trace, "logs"));
    }
}
