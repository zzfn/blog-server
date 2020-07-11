package com.api.jello.controller;

import com.api.jello.dao.CommentDao;
import com.api.jello.dao.ReplyDao;
import com.api.jello.entity.Article;
import com.api.jello.entity.Comment;
import com.api.jello.entity.Reply;
import com.api.jello.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * COMMENT(TComment)表控制层
 *
 * @author nanaouyang
 * @since 2020-04-09 21:12:45
 */
@RestController
@RequestMapping("comment")
@Slf4j
public class CommentController {
    @Autowired
    CommentDao commentDao;
    @Autowired
    ReplyDao replyDao;

    /**
     * 分页查询文字列表
     * @param id
     * @return
     */
    @GetMapping("listComments")
    public Object listComments(String id) {
        List<Comment> commentList=commentDao.selectList(new QueryWrapper<Comment>().eq("ARTICLE_ID",id));
        commentList.forEach(item-> item.setReplyList(replyDao.selectList(new QueryWrapper<Reply>().eq("COMMENT_ID",item.getId()))));
        return ResultUtil.success(commentList);
    }
    @PostMapping("saveComment")
    public Object saveComment(@RequestBody Comment comment) {
        log.info("1111sad");
        return ResultUtil.success(commentDao.insert(comment));
    }
}