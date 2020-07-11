package com.api.jello.service.impl;


import com.api.jello.dao.ArticleDao;
import com.api.jello.entity.Article;
import com.api.jello.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Override
    public Article getArticle(String id) {
        return articleDao.getArticle(id);
    }
}
