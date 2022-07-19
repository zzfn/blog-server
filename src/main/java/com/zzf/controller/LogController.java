package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.LogEs;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PageVO;
import com.zzf.vo.RequestVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("log")
@Slf4j
public class LogController {
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("search")
    public Object getList(PageVO pageVO) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withSort(SortBuilders.fieldSort("time").order(SortOrder.DESC));
        queryBuilder.withPageable(PageRequest.of(pageVO.getCurrent()-1, pageVO.getPageSize()));
        NativeSearchQuery nativeSearchQuery = queryBuilder.build();
        SearchHits<LogEs> esSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery, LogEs.class);
        List<LogEs> searchProductList = esSearchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        HashMap<String,Object> map=new HashMap<>();
        map.put("total",esSearchHits.getTotalHits());
        map.put("records",searchProductList);
        return ResultUtil.success(map);
    }
    @PostMapping("delete")
    public Object deleteLog(@RequestBody RequestVO requestVO) {
        String result = elasticsearchRestTemplate.delete(requestVO.getId(), LogEs.class);
        return ResultUtil.success(result);
    }
}
