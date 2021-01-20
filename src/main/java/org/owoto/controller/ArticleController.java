package org.owoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.owoto.entity.ArticleEs;
import org.owoto.mapper.ArticleDao;
import org.owoto.mapper.ArticleESDao;
import org.owoto.entity.Article;
import org.owoto.service.ArticleService;
import org.owoto.util.RedisUtil;
import org.owoto.util.ResultUtil;
import org.owoto.vo.PageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzf
 */
@RestController
@RequestMapping("article")
@Slf4j
@Api(tags = "文章管理")
public class ArticleController {
    @Autowired
    ArticleDao articleMapper;
    @Autowired
    ArticleService articleService;
    @Autowired
    private ArticleESDao articleESDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("saveArticle")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("保存或修改文章")
    public Object saveArticle(@RequestBody Article article) {
        return ResultUtil.success(articleService.saveOrUpdate(article));
    }

    @ApiOperation("文章分页列表")
    @GetMapping("non/page")
    public Object listArticles(PageVO pageVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IS_RELEASE",0).orderByDesc("ORDER_NUM").orderByDesc("CREATE_TIME");
        IPage<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        IPage<Article> pageList = articleMapper.selectPage(page, queryWrapper);
        pageList.getRecords().forEach(article -> {
            if (redisUtil.get(article.getId()) == null) {
                redisUtil.incr(article.getId(), 1);
                article.setViewCount(0L);
            } else {
                article.setViewCount(((Number) redisUtil.get(article.getId())).longValue());
            }
        });
        return ResultUtil.success(pageList);
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
    public Object listArchives(String code) {
        return ResultUtil.success(articleMapper.getArchives(code));
    }

    @ApiOperation("根据id查询文章详情")
    @GetMapping("non/{id}")
    public Object getArticle(@PathVariable("id") String id) {
        Article article = articleMapper.selectById(id);
        if (article != null) {
            article.setViewCount(redisUtil.incr(id, 1));
            return ResultUtil.success(article);
        } else {
            return ResultUtil.error("未找到结果");
        }
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("removeArticle")
    public Object removeArticle(@RequestBody Article article) {
        articleESDao.deleteById(article.getId());
        return ResultUtil.success(articleMapper.deleteById(article.getId()));
    }

    @GetMapping("non/search")
    public Object getList(String keyword) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.should(QueryBuilders.matchPhraseQuery("title", keyword))
                .should(QueryBuilders.matchPhraseQuery("content", keyword))
                .should(QueryBuilders.matchPhraseQuery("tag_desc", keyword));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withHighlightBuilder(new HighlightBuilder().field("title").field("content").field("tag_desc")).build();
        List<ArticleEs> list = new ArrayList<>();
        SearchHits<ArticleEs> articleES = elasticsearchRestTemplate.search(nativeSearchQuery, ArticleEs.class);
        articleES.getSearchHits().forEach(searchHit -> {
            ArticleEs articleES1 = searchHit.getContent();
            articleES1.setContent(StringUtils.join(searchHit.getHighlightField("content"), " "));
            if (searchHit.getHighlightField("tagDesc").size() != 0) {
                articleES1.setTagDesc(StringUtils.join(searchHit.getHighlightField("tagDesc"), " "));
            }
            if (searchHit.getHighlightField("title").size() != 0) {
                articleES1.setTitle(StringUtils.join(searchHit.getHighlightField("title"), " "));
            }
            list.add(articleES1);
        });
        return ResultUtil.success(list);
    }
}
