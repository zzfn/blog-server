package com.api.jello.controller;

import com.api.jello.dao.ArticleDao;
import com.api.jello.dao.CommentDao;
import com.api.jello.entity.Article;
import com.api.jello.util.ResultUtil;
import com.api.jello.vo.PageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzf
 */
@RestController
@RequestMapping("article")
@Slf4j
@Api(tags = "文章管理")
public class ArticleController {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    CommentDao commentDao;

    @PostMapping("saveArticle")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("保存或修改文章")
    public Object saveArticle(@RequestBody Article article) {
        article.setUpdateTime(null);
        if(null==article.getId()||null == articleDao.selectById(article.getId())){
            article.setViewCount(0);
            return ResultUtil.success(articleDao.insert(article));
        }else {
            return ResultUtil.success(articleDao.updateById(article));
        }
    }

    @ApiOperation("文章分页列表")
    @GetMapping("listArticles")
    public Object listArticles(PageVO pageVo,String title) {
        IPage<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        IPage<Article> pageList = articleDao.listArticle(page,title);
        return ResultUtil.success(pageList);
    }

    @ApiOperation("文章总数")
    @GetMapping("countArticles")
    public Object countArticles() {
        return ResultUtil.success(articleDao.selectCount(null));
    }

    @ApiOperation("文章分类")
    @GetMapping("listTags")
    public Object listTags() {
        return ResultUtil.success(articleDao.getTags());
    }

    @ApiOperation("文章列表不分页")
    @GetMapping("listArchives")
    public Object listArchives(String code) {
        return ResultUtil.success(articleDao.getArchives(code));
    }

    @ApiOperation("根据id查询文章详情")
    @GetMapping("getArticle")
    public Object getArticle(String id) {
        return ResultUtil.success(articleDao.getArticle(id));
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("removeArticle")
    public Object removeArticle(@RequestBody Article article) {
        return ResultUtil.success(articleDao.deleteById(article.getId()));
    }
}
