package com.zzf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Favorite;
import com.zzf.service.FavoriteService;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PageVO;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("list")
    @IgnoreAuth
    public Object selectAllList() {
        return ResultUtil.success(favoriteService.getAllFavorite());
    }

    @GetMapping("allTag")
    public Object selectAllTag() {
        return ResultUtil.success(favoriteService.selectTags());
    }

    @GetMapping("page")
    public Object selectPage(PageVO pageVo, String title) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ORDER_NUM").orderByDesc("CREATE_TIME");
        queryWrapper.like(null!=title,"TITLE", title);
        IPage<Favorite> page = new Page<>(pageVo.getCurrent(), pageVo.getPageSize());
        IPage<Favorite> pageList = favoriteService.page(page, queryWrapper);
        return ResultUtil.success(pageList);
    }

    @GetMapping("")
    public Object selectAll(@RequestParam String id) {
        return ResultUtil.success(favoriteService.getById(id));
    }

    @DeleteMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public Object removeById(@RequestParam String id) {
        return ResultUtil.success(favoriteService.removeById(id));
    }

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public Object saveOne(@RequestBody Favorite favorite) {
        this.favoriteService.saveOrUpdate(favorite);
        this.favoriteService.refreshAllFavorite();
        return ResultUtil.success(favorite.getId());
    }
}
