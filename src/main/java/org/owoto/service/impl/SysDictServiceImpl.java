package org.owoto.service.impl;

import com.alibaba.fastjson.JSON;
import org.owoto.entity.SysDict;
import org.owoto.mapper.SysDictDao;
import org.owoto.service.SysDictService;
import org.owoto.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * DICT(SysDict)表服务实现类
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:20
 */
@Service
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDict> implements SysDictService {

    @Autowired
    SysDictDao sysDictDao;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String translate(String typeCode, String code) {
        List<SysDict> list = JSON.parseArray(JSON.toJSONString(redisUtil.get("dict::" + typeCode)), SysDict.class);
        final String[] name = {""};
        if(null!=list){
            list.forEach(sysDict -> {
                if(sysDict.getCode().equals(code)){
                    name[0] =sysDict.getName();
                }
            });
            return name[0];
        }else {
            return null;
        }
    }

    @Override
    @Cacheable(value = "dict", key = "#code")
    public List<SysDict> getDict(String code) {
        return sysDictDao.selectList(new QueryWrapper<SysDict>().eq("TYPE_CODE", code).orderByDesc("ORDER_NUM"));
    }

    @Override
    @CachePut(value = "dict", key = "#sysDict.typeCode")
    public Object saveDict(@RequestBody SysDict sysDict) {
        if (StringUtils.isNotEmpty(sysDict.getId())) {
            sysDictDao.updateById(sysDict);
        } else {
            sysDictDao.insert(sysDict);
        }
        return this.getDict(sysDict.getTypeCode());
    }
}