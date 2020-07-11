package com.api.jello.dao;

import com.api.jello.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;

/**
 * @author cc
 */
@Repository
public interface ArticleDao extends BaseMapper<Article> {
    Article getArticle(String id);

    IPage<Article> listArticle(IPage<Article> page);
}
