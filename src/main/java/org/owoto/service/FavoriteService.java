package org.owoto.service;

import org.owoto.entity.Article;
import org.owoto.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface FavoriteService extends IService<Favorite> {
    List<Favorite> selectFavorites();
}
