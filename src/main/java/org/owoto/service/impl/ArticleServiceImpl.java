package org.owoto.service.impl;


import org.owoto.dao.ArticleDao;
import org.owoto.entity.Article;
import org.owoto.service.ArticleService;
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
