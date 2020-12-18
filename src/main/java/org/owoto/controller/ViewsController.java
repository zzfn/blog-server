package org.owoto.controller;

import org.owoto.dao.ViewsDao;
import org.owoto.entity.Views;
import org.owoto.vo.RequestVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cc
 * @date 2020/3/22 17:22
 */
@RestController
@RequestMapping("views")
@Slf4j
@Api(tags = "文章浏览量管理")
public class ViewsController {
    @Autowired
    ViewsDao viewsDao;

    @PostMapping("updateViews")
    public Object updateViews(@RequestBody RequestVO requestVO) {
        Views views=viewsDao.selectOne(new QueryWrapper<Views>().eq("ARTICLE_ID",requestVO.getId()));
        if(views!=null){
            viewsDao.update(views,new UpdateWrapper<Views>().setSql("COUNT=COUNT+1").eq("ARTICLE_ID",requestVO.getId()));
        }else {
            Views views1=new Views();
            views1.setId(null);
            views1.setCount(0);
            views1.setArticleId(requestVO.getId());
            viewsDao.insert(views1);
        }
        return null;
    }
}
