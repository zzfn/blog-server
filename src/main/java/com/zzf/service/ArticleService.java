package com.zzf.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzf.entity.Article;
import com.zzf.entity.ArticleMini;

import java.util.List;

public interface ArticleService extends IService<Article> {

    /**
     * 从redis缓存查询文章
     *
     * @param id 文章id
     * @return 返回文章详情
     */
    Article getByCache(String id);

    /**
     * 从数据库查找文章并缓存
     * @param id 文章id
     * @return 返回文章详情
     */
    Article getByDb(String id);

    /**
     * 从缓存中查文章详情
     * @param code
     * @return
     */
    List<ArticleMini> listByCache(String code);

    /**
     * 从数据库查询文章详情并缓存
     * @param code
     * @return
     */
    List<ArticleMini> listByDb(String code);
}
