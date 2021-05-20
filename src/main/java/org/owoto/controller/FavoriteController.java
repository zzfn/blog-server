package org.owoto.controller;


import org.owoto.entity.Favorite;
import org.owoto.service.FavoriteService;
import org.owoto.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * FAVORITE(Favorite)表控制层
 *
 * @author makejava
 * @since 2021-05-20 11:20:35
 */
@RestController
@RequestMapping("favorite")
public class FavoriteController {
    /**
     * 服务对象
     */
    @Resource
    private FavoriteService favoriteService;


    @GetMapping("non/all")
    public Object selectAll() {
        return ResultUtil.success(this.favoriteService.selectFavorites());
    }
    @PostMapping("save")
    public Object saveOne(@RequestBody Favorite favorite) {
        return ResultUtil.success(this.favoriteService.saveOrUpdate(favorite));
    }
}
