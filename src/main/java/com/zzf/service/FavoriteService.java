package com.zzf.service;

import com.zzf.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzf.vo.Labels;

import java.util.List;

/**
 *
 */
public interface FavoriteService extends IService<Favorite> {
    List<Favorite> getAllFavorite();
    List<Labels> selectTags();
}
