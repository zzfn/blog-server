package com.zzf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzf.entity.Article;
import com.zzf.service.ArticleService;
import com.zzf.service.TraceService;
import com.zzf.util.RedisUtil;
import com.zzf.vo.Tags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.zzf.mapper.ArticleDao;
import com.zzf.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cc
 */
@RestController
@RequestMapping("overview")
@Slf4j
@Api(tags = "统计接口")
public class OverviewController {
    @Resource
    ArticleService articleService;
    @Resource
    TraceService traceService;
    @Resource
    ArticleDao articleDao;
    @Resource
    RedisUtil redisUtil;

    @ApiOperation("后台工作台统计接口")
    @GetMapping("/getHomeOverview")
    public Object getHomeOverview() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        long allCount = articleService.count(lambdaQueryWrapper);
        lambdaQueryWrapper.eq(Article::getIsRelease, true);
        long releaseCount = articleService.count(lambdaQueryWrapper);
        List<Tags> tags = articleDao.getTags();
        Object performances = traceService.getPerformance();
        Map<String, Object> map = new HashMap<>();
        map.put("allCount", allCount);
        map.put("releaseCount", releaseCount);
        map.put("tags", tags);
        map.put("performances", performances);
        map.put("searchKeywords",redisUtil.reverseRangeWithScores("searchKeywords", 0L, 9L));
        return ResultUtil.success(map);
    }
}
