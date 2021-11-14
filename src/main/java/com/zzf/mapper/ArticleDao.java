package com.zzf.mapper;

import com.zzf.entity.Article;
import com.zzf.entity.ArticleMini;
import com.zzf.vo.LabelValueVO;
import com.zzf.vo.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cc
 */
@Repository
public interface ArticleDao extends BaseMapper<Article> {
    Article getArticle(String id);

    IPage<Article> listArticle(IPage<Article> page, @Param("title") String title);

    List<Tags> getTags();

    List<ArticleMini> getArchives(String code);

    List<ArticleMini> listLastUpdated();

    List<LabelValueVO> overviewCount();
}
