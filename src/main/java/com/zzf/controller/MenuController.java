package com.zzf.controller;

import com.zzf.entity.Menu;
import com.zzf.service.MenuService;
import com.zzf.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author c.chen
 */
@RestController
@RequestMapping("menu")
@Slf4j
public class MenuController {
    @Resource
    MenuService menuService;

    @GetMapping("list")
    public Object menuList() {
        return ResultUtil.success(menuService.list(null));
    }
    @GetMapping("{id}")
    public Object menuOne(@PathVariable("id") String id) {
        return ResultUtil.success(menuService.getById(id));
    }
    @PostMapping("save")
    public Object menuSave(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return ResultUtil.success(menu.getId());
    }
}
