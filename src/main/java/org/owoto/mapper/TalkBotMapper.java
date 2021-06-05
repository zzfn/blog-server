package org.owoto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lettuce.core.dynamic.annotation.Param;
import org.owoto.entity.Article;
import org.owoto.entity.ArticleMini;
import org.owoto.entity.TalkBot;
import org.owoto.vo.Tags;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cc
 */
@Repository
public interface TalkBotMapper extends BaseMapper<TalkBot> {

}
