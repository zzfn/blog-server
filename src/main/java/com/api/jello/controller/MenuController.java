package com.api.jello.controller;

import com.api.jello.dao.MenuDao;
import com.api.jello.entity.Menu;
import com.api.jello.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    MenuDao menuDao;

    @PostMapping("saveMenu")
    public Object saveMenu(@RequestBody Menu menu) {
        return ResultUtil.success(menuDao.insert(menu));
    }
    @GetMapping("getMenus")
    public Object getMenus() {
        return ResultUtil.success(menuDao.selectList(null));
    }
}