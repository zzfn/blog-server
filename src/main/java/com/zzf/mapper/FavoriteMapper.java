package com.zzf.mapper;

import com.zzf.entity.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzf.vo.Labels;

import java.util.List;

/**
 * @Entity Favorite
 */
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<Favorite> selectFavorites();
    List<Labels> selectTags();
}




