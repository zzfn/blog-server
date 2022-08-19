package com.zzf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.Menu;
import com.zzf.mapper.MenuMapper;
import com.zzf.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zzf
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
