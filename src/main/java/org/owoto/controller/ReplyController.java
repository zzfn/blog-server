package org.owoto.controller;

import org.owoto.dao.ReplyDao;
import org.owoto.entity.Reply;
import org.owoto.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REPLY(TReply)表控制层
 *
 * @author nanaouyang
 * @since 2020-04-12 13:39:56
 */
@RestController
@RequestMapping("reply")
public class ReplyController {
    @Autowired
    ReplyDao replyDao;
    @PostMapping("saveReply")
    public Object saveReply(@RequestBody Reply reply) {
        return ResultUtil.success(replyDao.insert(reply));
    }
}