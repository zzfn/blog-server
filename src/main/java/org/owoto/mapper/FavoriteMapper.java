package org.owoto.mapper;

import org.owoto.entity.Article;
import org.owoto.entity.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity org.owoto.entity.Favorite
 */
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<Favorite> selectFavorites();
}




