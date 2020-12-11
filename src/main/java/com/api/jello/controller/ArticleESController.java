package com.api.jello.controller;

import com.api.jello.dao.ArticleESDao;
import com.api.jello.entity.ArticleES;
import com.api.jello.util.ResultUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzfn
 * @date 2020-12-10 14:58
 */
@RestController
@RequestMapping("es")
public class ArticleESController {
    @Autowired
    private ArticleESDao articleESDao;

    @PostMapping("test")
    public Object listTags(@RequestBody ArticleES articleES) {
        return ResultUtil.success(articleESDao.save(articleES));
    }
    @GetMapping("list")
    public Object getList(String keyword) {
        BoolQueryBuilder queryBuilder= QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchPhraseQuery("title",keyword))
                .should(QueryBuilders.matchPhraseQuery("content",keyword));
        articleESDao.search(queryBuilder);
        return ResultUtil.success(articleESDao.search(queryBuilder));
    }
}
