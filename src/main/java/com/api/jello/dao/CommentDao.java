package com.api.jello.dao;

import com.api.jello.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * COMMENT(TComment)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-04-09 21:12:45
 */
@Repository
public interface CommentDao extends BaseMapper<Comment> {


}