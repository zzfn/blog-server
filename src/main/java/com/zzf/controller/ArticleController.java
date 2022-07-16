package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.component.Send;
import com.zzf.entity.Article;
import com.zzf.entity.ArticleEs;
import com.zzf.service.ArticleService;
import com.zzf.util.HttpUtil;
import com.zzf.util.IpUtil;
import com.zzf.util.RedisUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.ArticleTagVO;
import com.zzf.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zzf
 */
@RestController
@RequestMapping("article")
@Slf4j
public class ArticleController {
    static final String TAG_DESC = "tagDesc";
    static final String TITLE = "title";
    static final String CONTENT = "content";
    static final String ADMIN = "admin";

    @Resource
    ArticleService articleService;
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    RedisUtil redisUtil;

    @Resource
    private Send send;

    @GetMapping("page")
    @IgnoreAuth
    public Object pageArticles(ArticleVO articleVO) {
        String system = HttpUtil.getSystem();
        if (ADMIN.equals(system)) {
            return ResultUtil.success(articleService.pageList(articleVO, false));
        } else {
            return ResultUtil.success(articleService.pageList(articleVO, true));
        }
    }

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public Object saveArticle(@RequestBody Article article) {
        articleService.saveOrUpdate(article);
        if (article.getIsRelease()) {
            send.post(article);
        }
        return ResultUtil.success(article.getId());
    }

    @GetMapping("hot")
    public Object listHotArticles() {
        Collection ids = new ArrayList();
        List<Double> num = new ArrayList();
        Set<ZSetOperations.TypedTuple<Object>> typedTuple1 = redisUtil.reverseRangeWithScores("viewCount", 0L, 9L);
        typedTuple1.forEach(objectTypedTuple -> {
            ids.add(objectTypedTuple.getValue());
            num.add(objectTypedTuple.getScore());
        });
        List<Article> articles = articleService.listByIds(ids);
        for (int i = 0; i < articles.size(); i++) {
            articles.get(i).setViewCount(num.get(i).longValue());
        }
        return ResultUtil.success(articles);
    }

    @GetMapping("tags")
    @IgnoreAuth
    public Object listTags() {
        return ResultUtil.success(articleService.getTagsCount());
    }

    @GetMapping("list")
    @IgnoreAuth
    public Object listArchives(@RequestParam(defaultValue = "") String code) {
        ArticleTagVO articleTagVO=new ArticleTagVO();
        articleTagVO.setTagCode(code);
        articleTagVO.setArticleList(articleService.listByCache(code));
        return ResultUtil.success(articleTagVO);
    }

    @GetMapping("lastUpdated")
    @IgnoreAuth
    public Object lastUpdated() {
        return ResultUtil.success(articleService.listLastUpdated());
    }

    @GetMapping("updateViewed")
    public Object updateViewed(@RequestParam String id) {
        String isViewed = "isViewed::" + IpUtil.getIp() + "::" + id;
        if (redisUtil.hasKey(isViewed)) {
            return ResultUtil.success(false);
        } else {
            redisUtil.set(isViewed, 1, 3600L);
            redisUtil.incZSetValue("viewCount", id, 1L);
            return ResultUtil.success(true);
        }
    }

    @PostMapping("star")
    public Object star(@RequestParam String id) {
        String isStared = "isStared::" + IpUtil.getIp() + "::" + id;
        if (redisUtil.hasKey(isStared)) {
            return ResultUtil.success(false);
        } else {
            redisUtil.set(isStared, 1);
            redisUtil.incZSetValue("starCount", id, 1L);
            return ResultUtil.success(true);
        }
    }

    @GetMapping("{id}")
    @IgnoreAuth
    public Object getArticle(@PathVariable("id") String id) {
        String system = HttpUtil.getSystem();
        if (ADMIN.equals(system)) {
            Article article = articleService.getByDb(id);
            return ResultUtil.success(article);
        } else {
            if (StringUtils.isBlank(id)) {
                return ResultUtil.error("请传文章id");
            }
            Article article = articleService.getByCache(id);
            if (null == article || !article.getIsRelease()) {
                return ResultUtil.error("文章已下线");
            }
            Double num = redisUtil.zScore("viewCount", id);
            if (null == num) {
                article.setViewCount(1L);
            } else {
                article.setViewCount(num.longValue());
            }
            return ResultUtil.success(article);
        }
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object removeArticle(@PathVariable String id) {
        return ResultUtil.success(articleService.delByDb(id));
    }

    @GetMapping("search")
    @IgnoreAuth
    public Object getList(String keyword) {
        redisUtil.incZSetValue("searchKeywords", keyword, 1L);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchPhraseQuery(TITLE, keyword))
                .should(QueryBuilders.matchPhraseQuery(CONTENT, keyword))
                .should(QueryBuilders.matchPhraseQuery("tag_desc", keyword));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withHighlightBuilder(new HighlightBuilder().field(TITLE).field(CONTENT).field("tag_desc")).build();
        List<ArticleEs> list = new ArrayList<>();
        SearchHits<ArticleEs> esSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery, ArticleEs.class);
        esSearchHits.getSearchHits().forEach(searchHit -> {
            ArticleEs articleEs = searchHit.getContent();
            articleEs.setContent(StringUtils.join(searchHit.getHighlightField(CONTENT), " "));
            if (searchHit.getHighlightField(TAG_DESC).size() != 0) {
                articleEs.setTagDesc(StringUtils.join(searchHit.getHighlightField(TAG_DESC), " "));
            }
            if (searchHit.getHighlightField(TITLE).size() != 0) {
                articleEs.setTitle(StringUtils.join(searchHit.getHighlightField(TITLE), " "));
            }
            if (articleEs.getIsRelease() == 1 && articleEs.getIsDelete() == 0) {
                list.add(articleEs);
            }
        });
        return ResultUtil.success(list);
    }
}