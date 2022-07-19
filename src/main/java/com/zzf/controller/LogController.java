package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.ArticleEs;
import com.zzf.entity.LogEs;
import com.zzf.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("log")
public class LogController {
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("search")
    @IgnoreAuth
    public Object getList(String keyword) {
        List<LogEs> list = new ArrayList<>();
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().build();
        SearchHits<LogEs> esSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery, LogEs.class);
        return ResultUtil.success(esSearchHits);
    }
}
