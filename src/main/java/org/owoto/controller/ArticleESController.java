package org.owoto.controller;

import org.owoto.mapper.ArticleESDao;
import org.owoto.entity.ArticleEs;
import org.owoto.util.ResultUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
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
    public Object listTags(@RequestBody ArticleEs articleES) {
        return ResultUtil.success(articleESDao.save(articleES));
    }
    @PostMapping("del")
    public Object del() {
        articleESDao.deleteAll();
        elasticsearchRestTemplate.deleteIndex("article");
        return ResultUtil.success(null);
    }
    @GetMapping("list")
    public Object getList(String keyword) {
        BoolQueryBuilder queryBuilder= QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchPhraseQuery("title",keyword))
                .should(QueryBuilders.matchPhraseQuery("content",keyword))
                .should(QueryBuilders.matchPhraseQuery("tag_desc",keyword));
        Iterable<ArticleEs> pages=articleESDao.search(queryBuilder);
        List<ArticleEs> list=new ArrayList<>();
        pages.forEach(list::add);
        return ResultUtil.success(list);
    }
}
