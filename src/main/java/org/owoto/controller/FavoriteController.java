package org.owoto.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.owoto.entity.Article;
import org.owoto.entity.Favorite;
import org.owoto.service.FavoriteService;
import org.owoto.util.ResultUtil;
import org.owoto.vo.PageVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

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
    @GetMapping("non/list")
    public Object selectAll() {
        return ResultUtil.success(favoriteService.list());
    }

    @GetMapping("non/page")
    public Object selectPage(PageVO pageVo) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ORDER_NUM").orderByDesc("CREATE_TIME");
        IPage<Favorite> page = new Page<>(pageVo.getCurrent(), pageVo.getPageSize());
        IPage<Favorite> pageList = favoriteService.page(page, queryWrapper);
        return ResultUtil.success(pageList);
    }

    @GetMapping("")
    public Object selectAll(@RequestParam String id) {
        return ResultUtil.success(favoriteService.getById(id));
    }
    @DeleteMapping("")
    @PreAuthorize("hasRole('admin')")
    public Object removeById(@RequestParam String id) {
        return ResultUtil.success(favoriteService.removeById(id));
    }
    @PostMapping("save")
    @PreAuthorize("hasRole('admin')")
    public Object saveOne(@RequestBody Favorite favorite) {
        this.favoriteService.saveOrUpdate(favorite);
        return ResultUtil.success(favorite.getId());
    }
}
