package com.api.jello.controller;

import com.api.jello.dao.ArticleESDao;
import com.api.jello.entity.ArticleES;
import com.api.jello.entity.Pages;
import com.api.jello.util.ResultUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzfn
 * @date 2020-12-10 14:58
 */
@RestController
@RequestMapping("es")
public class ArticleESController {
    @Autowired
    private ArticleESDao articleESDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostMapping("test")
    public Object listTags(@RequestBody ArticleES articleES) {
        return ResultUtil.success(articleESDao.save(articleES));
    }

    @GetMapping("list")
    public Object getList(String keyword, Integer page, Integer size) {
//        Pageable pageable=PageRequest.of(page,size);
        BoolQueryBuilder queryBuilder= QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchPhraseQuery("title",keyword))
                .should(QueryBuilders.matchPhraseQuery("content",keyword));
        Iterable<ArticleES> page1=articleESDao.search(queryBuilder);
        List<ArticleES> list=new ArrayList<>();
        page1.forEach(list::add);
//        Pages<ArticleES> esPages=new Pages<>();
//        esPages.setRecords(page1.getContent());
//        esPages.setTotalPage(page1.getTotalPages());
//        esPages.setSize(page1.getSize());
//        esPages.setPage(page1.getNumber());
//        esPages.setTotalSize(page1.getTotalElements());
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(new BoolQueryBuilder().should(new MatchQueryBuilder("title", keyword)).should(new MatchQueryBuilder("content", keyword))).withPageable(PageRequest.of(page, size)).build();
//        Page<ArticleES> page1 = elasticsearchRestTemplate.queryForPage(nativeSearchQuery, ArticleES.class);
//        Pages<ArticleES> esPages = new Pages<>();
//        esPages.setRecords(page1.getContent());
//        esPages.setTotalPage(page1.getTotalPages());
//        esPages.setSize(page1.getSize());
//        esPages.setPage(page1.getNumber());
//        esPages.setTotalSize(page1.getTotalElements());
        return ResultUtil.success(list);
    }
}
