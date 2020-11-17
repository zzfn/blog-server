package com.api.jello.controller;

import com.api.jello.dao.ArticleDao;
import com.api.jello.dao.CommentDao;
import com.api.jello.dao.ViewsDao;
import com.api.jello.entity.Article;
import com.api.jello.entity.Views;
import com.api.jello.util.ResultUtil;
import com.api.jello.vo.PageVO;
import com.api.jello.vo.RequestVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author cc
 * @date 2020/3/22 17:22
 */
@RestController
@RequestMapping("views")
@Slf4j
public class ViewsController {
    @Autowired
    ViewsDao viewsDao;

    @PostMapping("updateViews")
    public Object updateViews(@RequestBody RequestVO requestVO) {
        Views views=viewsDao.selectOne(new QueryWrapper<Views>().eq("ARTICLE_ID",requestVO.getId()));
        if(views!=null){
            Views views1=new Views();
            views.setArticleId(requestVO.getId());
            viewsDao.update(views1,new UpdateWrapper<Views>().setSql("COUNT=COUNT+1").eq("ARTICLE_ID",requestVO.getId()));
        }else {
            Views views1=new Views();
            views1.setCount(0);
            views1.setArticleId(requestVO.getId());
            viewsDao.insert(views1);
        }
        return null;
    }
}
