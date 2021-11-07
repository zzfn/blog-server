package com.zzf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzf.entity.Article;
import com.zzf.entity.ArticleMini;
import com.zzf.mapper.ArticleDao;
import com.zzf.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.vo.ArticleVO;
import com.zzf.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author zzf
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Resource
    ArticleDao articleMapper;

    @Override
    @Cacheable(value = "ARTICLE_PAGE", key = "#articleVO.current", condition = "#isCached == true")
    public IPage<Article> pageList(ArticleVO articleVO, boolean isCached) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(isCached, Article::getIsRelease, 1).orderByDesc(Article::getOrderNum);
        lambdaQueryWrapper.eq(null != articleVO.getId(), Article::getId, articleVO.getId());
        lambdaQueryWrapper.orderBy("updateTime".equals(articleVO.getField()), "ascend".equals(articleVO.getOrder()), Article::getUpdateTime);
        lambdaQueryWrapper.orderBy("createTime".equals(articleVO.getField()), "ascend".equals(articleVO.getOrder()), Article::getCreateTime);
        lambdaQueryWrapper.eq(null != articleVO.getTag(), Article::getTag, articleVO.getTag());
        lambdaQueryWrapper.eq(null != articleVO.getIsRelease(), Article::getIsRelease, articleVO.getIsRelease());
        lambdaQueryWrapper.like(StringUtils.isNoneEmpty(articleVO.getTitle()), Article::getTitle, articleVO.getTitle());
        IPage<Article> page = new Page<>(articleVO.getCurrent(), articleVO.getPageSize());
        return articleMapper.selectPage(page, lambdaQueryWrapper);
    }


    @Override
    @Cacheable(value = "ARTICLE_DETAIL", key = "#id")
    public Article getByCache(String id) {
        return articleMapper.selectById(id);
    }

    @Override
    @CacheEvict(value = "ARTICLE_DETAIL", key = "#id")
    public Object delByDb(String id) {
        return articleMapper.deleteById(id);
    }

    @Override
    @CachePut(value = "ARTICLE_DETAIL", key = "#id")
    public Article getByDb(String id) {
        return articleMapper.selectById(id);
    }

    @Override
    @Cacheable(value = "ALL_ARTICLE", key = "#code")
    public List<ArticleMini> listByCache(String code) {
        return articleMapper.getArchives(code);
    }

    @Override
    @CachePut(value = "ALL_ARTICLE", key = "#code")
    public List<ArticleMini> listByDb(String code) {
        return articleMapper.getArchives(code);
    }
}
