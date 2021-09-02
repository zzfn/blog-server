package com.zzf.mapper;

import com.zzf.entity.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzf.vo.Labels;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenchen
 * @Entity Favorite
 */
@Repository
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<Favorite> selectFavorites();
    List<Labels> selectTags();
}




