package com.api.jello.service;


import com.api.jello.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ArticleService extends IService<Article> {
    Article getArticle(String id);
}
