package org.owoto.controller;

import org.owoto.dao.MenuDao;
import org.owoto.entity.Menu;
import org.owoto.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("menu")
@Api(tags = "菜单管理")
public class MenuController {
    @Autowired
    MenuDao menuDao;

    @PostMapping("saveMenu")
    public Object saveMenu(@RequestBody Menu menu) {
        if (menu.getId() != null) {
            return ResultUtil.success(menuDao.updateById(menu));
        } else {
            return ResultUtil.success(menuDao.insert(menu));
        }
    }

    @GetMapping("getMenus")
    public Object getMenus() {
        return ResultUtil.success(menuDao.selectList(null));
    }

    @GetMapping("getMenuById")
    public Object getMenuById(String id) {
        return ResultUtil.success(menuDao.selectById(id));
    }
}