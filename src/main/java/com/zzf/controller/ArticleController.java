package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.component.Send;
import com.zzf.entity.Article;
import com.zzf.entity.ArticleEs;
import com.zzf.util.HttpUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.ArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import com.zzf.mapper.ArticleDao;
import com.zzf.service.ArticleService;
import com.zzf.util.RedisUtil;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zzf
 */
@RestController
@RequestMapping("article")
@Slf4j
@Api(tags = "文章管理")
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
    @ApiOperation("文章分页列表")
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
    @ApiOperation("保存或修改文章")
    @PreAuthorize("hasRole('ADMIN')")
    public Object saveArticle(@RequestBody Article article) {
        articleService.saveOrUpdate(article);
        if (article.getIsRelease()) {
            send.post(article);
        }
        return ResultUtil.success(article.getId());
    }

    @ApiOperation("排行榜")
    @GetMapping("non/hot")
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

    @ApiOperation("文章分类")
    @GetMapping("tags")
    @IgnoreAuth
    public Object listTags() {
        return ResultUtil.success(articleService.getTagsCount());
    }

    @ApiOperation("文章列表不分页")
    @GetMapping("list")
    @IgnoreAuth
    public Object listArchives(@RequestParam(defaultValue = "") String code) {
        return ResultUtil.success(articleService.listByCache(code));
    }

    @ApiOperation("最近更新")
    @GetMapping("lastUpdated")
    @IgnoreAuth
    public Object lastUpdated() {
        return ResultUtil.success(articleService.listLastUpdated());
    }

    @ApiOperation("更新浏览量")
    @GetMapping("updateViewed")
    public Object updateViewed(@RequestParam String id) {
        String isViewed = "isViewed::" + HttpUtil.getIp() + "::" + id;
        if (redisUtil.hasKey(isViewed)) {
            return ResultUtil.success(false);
        } else {
            redisUtil.set(isViewed, 1, 3600L);
            redisUtil.incZSetValue("viewCount", id, 1L);
            return ResultUtil.success(true);
        }
    }

    @ApiOperation("点赞")
    @PostMapping("non/star")
    public Object star(@RequestParam String id) {
        String isStared = "isStared::" + HttpUtil.getIp() + "::" + id;
        if (redisUtil.hasKey(isStared)) {
            return ResultUtil.success(false);
        } else {
            redisUtil.set(isStared, 1);
            redisUtil.incZSetValue("starCount", id, 1L);
            return ResultUtil.success(true);
        }
    }

    @ApiOperation("根据id查询文章详情")
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


    @ApiOperation("根据id删除文章")
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object removeArticle(@PathVariable String id) {
        return ResultUtil.success(articleService.delByDb(id));
    }

    @ApiOperation("es搜索")
    @GetMapping("search")
    @IgnoreAuth
    public Object getList(String keyword) {
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