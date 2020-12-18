package org.owoto.dao;

import org.owoto.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * REPLY(TReply)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-04-12 13:39:56
 */
@Repository
public interface ReplyDao extends BaseMapper<Reply> {


}