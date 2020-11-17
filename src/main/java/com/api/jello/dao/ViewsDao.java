package com.api.jello.dao;

import com.api.jello.entity.Article;
import com.api.jello.entity.Views;
import com.api.jello.vo.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cc
 */
@Repository
public interface ViewsDao extends BaseMapper<Views> {
}
