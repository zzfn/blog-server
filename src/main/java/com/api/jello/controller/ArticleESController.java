package com.api.jello.controller;

import com.api.jello.dao.ArticleESDao;
import com.api.jello.entity.ArticleES;
import com.api.jello.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
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

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @PostMapping("test")
    public Object listTags(@RequestBody ArticleES articleES) {
        return ResultUtil.success(articleESDao.save(articleES));
    }
    @GetMapping("list")
    public Object listTags() {
        return ResultUtil.success(articleESDao.findAll());
    }
}
