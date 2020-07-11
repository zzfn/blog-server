package com.api.jello.service;

import com.api.jello.config.Dict;
import com.api.jello.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * DICT(SysDict)表服务接口
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:19
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * @param typeCode 字典类型
     * @param code 字典值
     * @return 字典翻译后的中文
     */
    String translate(String typeCode, String code);

    List<SysDict> getDict(String code);

    Object saveDict(@RequestBody SysDict sysDict);
}