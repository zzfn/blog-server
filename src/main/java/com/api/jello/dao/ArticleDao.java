package com.api.jello.dao;

import com.api.jello.entity.Article;
import com.api.jello.vo.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cc
 */
@Repository
public interface ArticleDao extends BaseMapper<Article> {
    Article getArticle(String id);

    IPage<Article> listArticle(IPage<Article> page);

    List<Tags> getTags();

    List<Article> getArchives(String code);
}
