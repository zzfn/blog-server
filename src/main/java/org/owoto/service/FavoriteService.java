package org.owoto.service;

import org.owoto.entity.Article;
import org.owoto.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import org.owoto.vo.Labels;
import org.owoto.vo.Tags;

import java.util.List;

/**
 *
 */
public interface FavoriteService extends IService<Favorite> {
    List<Favorite> selectFavorites();
    List<Labels> selectTags();
}
