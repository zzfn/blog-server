package com.zzf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzf.entity.Article;
import com.zzf.service.ArticleService;
import com.zzf.service.LogUserService;
import com.zzf.util.RedisUtil;
import com.zzf.vo.Tags;
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
public class OverviewController {
    @Resource
    ArticleService articleService;
    @Resource
    ArticleDao articleDao;
    @Resource
    LogUserService logUserService;
    @Resource
    RedisUtil redisUtil;

    @GetMapping("/getHomeOverview")
    public Object getHomeOverview() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        long allCount = articleService.count(lambdaQueryWrapper);
        lambdaQueryWrapper.eq(Article::getIsRelease, true);
        long releaseCount = articleService.count(lambdaQueryWrapper);
        List<Tags> tags = articleDao.getTags();
        Map<String, Object> map = new HashMap<>();
        map.put("allCount", allCount);
        map.put("releaseCount", releaseCount);
        map.put("tags", tags);
        map.put("visitors", logUserService.visitorAnalysis());
        map.put("searchKeywords", redisUtil.reverseRangeWithScores("searchKeywords", 0L, -1L));
        return ResultUtil.success(map);
    }
}
