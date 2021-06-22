package org.owoto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.owoto.entity.Favorite;
import org.owoto.mapper.ArticleDao;
import org.owoto.service.FavoriteService;
import org.owoto.mapper.FavoriteMapper;
import org.owoto.vo.Labels;
import org.owoto.vo.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite>
implements FavoriteService{
    @Autowired
    FavoriteMapper favoriteMapper;
    @Override
    public List<Favorite> selectFavorites() {
        return favoriteMapper.selectFavorites();
    }

    @Override
    public List<Labels> selectTags() {
        return favoriteMapper.selectTags();
    }
}




