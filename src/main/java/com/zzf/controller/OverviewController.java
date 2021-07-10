package com.zzf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.zzf.mapper.ArticleDao;
import com.zzf.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("overview/non")
@Slf4j
@Api(tags = "统计接口")
public class OverviewController {
    @Autowired
    ArticleDao articleDao;

    @ApiOperation("按标签获取文章数量")
    @GetMapping("/tags")
    public Object listTags() {
        return ResultUtil.success(articleDao.overviewTags());
    }

    @ApiOperation("获取文章总数")
    @GetMapping("/all")
    public Object listArticles() {
        return ResultUtil.success(articleDao.overviewCount());
    }
}
