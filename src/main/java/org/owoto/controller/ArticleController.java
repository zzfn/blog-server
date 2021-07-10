package org.owoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.owoto.entity.Article;
import org.owoto.entity.ArticleEs;
import org.owoto.mapper.ArticleDao;
import org.owoto.mapper.ArticleESDao;
import org.owoto.service.ArticleService;
import org.owoto.util.HttpUtil;
import org.owoto.util.RedisUtil;
import org.owoto.util.ResultUtil;
import org.owoto.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    ArticleDao articleMapper;
    @Autowired
    ArticleService articleService;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private ArticleESDao articleESDao;
    @Autowired
    RedisUtil redisUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("saveArticle")
    @ApiOperation("保存或修改文章")
    public Object saveArticle(@RequestBody Article article) {
        // 执行保存逻辑
        articleService.saveOrUpdate(article);
        /**
         * 刷新文章缓存,如果发布了就刷新
         * 刷新列表缓存的逻辑
         * 是否发布状态改变
         * 标题改变
         * 以及删除
         */
        String ak = "ARTICLE_DETAIL::" + article.getId();
        boolean b2 = redisUtil.hasKey(ak);
        Article article0 = articleService.getByCache(article.getId());
        Article article1 = articleService.getByDb(article.getId());
        boolean b0 = article0.getTitle().equals(article1.getTitle());
        boolean b1 = article0.getIsRelease().equals(article1.getIsRelease());
        if (!b0 || !b1 || !b2) {
            articleService.listByDb("");
            articleService.listByDb(article1.getTag());
        }
        return ResultUtil.success(article.getId());
    }

    @ApiOperation("文章分页列表")
    @GetMapping("non/page")
    public Object listArticles(ArticleVO articleVO) {
        HashMap<String, String> map = new HashMap<>();
        map.put("updateTime", "UPDATE_TIME");
        map.put("createTime", "CREATE_TIME");
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(null != articleVO.getTag(), "TAG", articleVO.getTag()).eq(null != articleVO.getIsRelease(), "IS_RELEASE", articleVO.getIsRelease()).like(StringUtils.isNoneEmpty(articleVO.getTitle()), "TITLE", articleVO.getTitle()).eq(articleVO.getIsOnlyRelease(), "IS_RELEASE", 1).orderBy(null != articleVO.getField(), "ascend".equals(articleVO.getOrder()), map.get(articleVO.getField())).orderByDesc("ORDER_NUM").orderByDesc("CREATE_TIME");
        IPage<Article> page = new Page<>(articleVO.getCurrent(), articleVO.getPageSize());
        IPage<Article> pageList = articleMapper.selectPage(page, queryWrapper);
        return ResultUtil.success(pageList);
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

    @ApiOperation("文章总数")
    @GetMapping("countArticles")
    public Object countArticles() {
        return ResultUtil.success(articleMapper.selectCount(null));
    }

    @ApiOperation("文章分类")
    @GetMapping("/non/tags")
    public Object listTags() {
        return ResultUtil.success(articleMapper.getTags());
    }

    @ApiOperation("文章列表不分页")
    @GetMapping("non/list")
    public Object listArchives(@RequestParam(defaultValue = "") String code) {
        return ResultUtil.success(articleService.listByCache(code));
    }

    @ApiOperation("最近更新")
    @GetMapping("non/lastUpdated")
    public Object lastUpdated() {
        return ResultUtil.success(articleMapper.listLastUpdated());
    }

    @ApiOperation("更新浏览量")
    @GetMapping("non/updateViewed")
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

    @ApiOperation("根据id查询文章详情-前台")
    @GetMapping("non/{id}")
    public Object getArticle(@PathVariable("id") String id) {
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

    @ApiOperation("根据id查询文章详情-后台")
    @GetMapping("{id}")
    public Object getArticleAdmin(@PathVariable("id") String id) {
        Article article = articleService.getByDb(id);
        return ResultUtil.success(article);
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("non/{id}/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object removeArticle(@PathVariable String id, @PathVariable String code) {
        articleService.listByDb("");
        articleService.listByDb(code);
        redisUtil.zRemove(id);
        return ResultUtil.success(articleMapper.deleteById(id));
    }

    @ApiOperation("es搜索")
    @GetMapping("non/search")
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

    @GetMapping("es/non/reset")
    public Object reset() {
        elasticsearchRestTemplate.createIndex(ArticleEs.class);
        elasticsearchRestTemplate.putMapping(ArticleEs.class);
        return null;
    }

    @GetMapping("es/non/del")
    public Object del() {
        elasticsearchRestTemplate.deleteIndex(ArticleEs.class);
        return null;
    }
//    @GetMapping("es/reset")
//    @PreAuthorize("hasRole('ADMIN')")
//    public Object reset() {
//        articleESDao.deleteAll();
//        return null;
//    }

}
