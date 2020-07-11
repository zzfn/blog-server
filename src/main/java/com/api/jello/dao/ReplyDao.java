package com.api.jello.dao;

import com.api.jello.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * REPLY(TReply)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-04-12 13:39:56
 */
@Repository
public interface ReplyDao extends BaseMapper<Reply> {


}