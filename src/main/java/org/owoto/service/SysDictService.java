package org.owoto.service;

import org.owoto.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Object saveDict(SysDict sysDict);
}