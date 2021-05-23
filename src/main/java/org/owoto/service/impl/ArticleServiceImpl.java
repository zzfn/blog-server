package org.owoto.service.impl;


import org.owoto.entity.Article;
import org.owoto.entity.ArticleMini;
import org.owoto.mapper.ArticleDao;
import org.owoto.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzf
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    @Cacheable(value = "ARTICLE_DETAIL", key = "#id")
    public Article getByCache(String id) {
        return articleDao.selectById(id);
    }

    @Override
    @CachePut(value = "ARTICLE_DETAIL", key = "#id")
    public Article getByDb(String id) {
        return articleDao.selectById(id);
    }

    @Override
    @Cacheable(value = "ALL_ARTICLE", key = "#code")
    public List<ArticleMini> listByCache(String code) {
        return articleDao.getArchives(code);
    }

    @Override
    @CachePut(value = "ALL_ARTICLE", key = "#code")
    public List<ArticleMini> listByDb(String code) {
        return articleDao.getArchives(code);
    }
}
