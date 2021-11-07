package com.zzf.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzf.entity.Article;
import com.zzf.entity.ArticleMini;
import com.zzf.vo.ArticleVO;
import com.zzf.vo.PageVO;
import com.zzf.vo.Tags;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cc
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页查询结果
     * @param articleVO vo类
     * @param isCached 是否查询缓存
     * @return 分页结果
     */
    IPage<Article> pageList(@Param("pageVO") ArticleVO articleVO, @Param("isCache") boolean isCached);

    /**
     * 查询最近更新的5条记录
     * @return  最近更新的5条记录
     */
    List<ArticleMini> listLastUpdated();

    List<Tags> getTagsCount();
    /**
     * 从redis缓存查询文章
     *
     * @param id 文章id
     * @return 返回文章详情
     */
    Article getByCache(String id);

    /**
     * 数据库和redis删除缓存
     * @param id 文章id
     * @return 数据库和redis删除缓存
     */
    Object delByDb(String id);
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
