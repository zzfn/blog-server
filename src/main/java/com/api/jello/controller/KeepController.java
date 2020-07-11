package com.api.jello.controller;

import com.api.jello.dao.KeepDao;
import com.api.jello.entity.Keep;
import com.api.jello.entity.Reply;
import com.api.jello.service.KeepService;
import com.api.jello.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("keep")
@Slf4j
public class KeepController {
    @Autowired
    KeepDao keepDao;
    @Autowired
    KeepService keepService;
    /**
     * 新增记账记录
     * @param keep
     * @return
     */
    @PostMapping("saveKeep")
    public Object saveKeep(@RequestBody Keep keep) {
        return ResultUtil.success(keepDao.insert(keep));
    }

    /**
     * 查询所有记账记录
     * @return
     */
    @GetMapping("listKeeps")
    public Object listKeeps(String time,String type) {
        return ResultUtil.success(keepDao.selectList(new QueryWrapper<Keep>().orderByDesc("CREATE_TIME")));
    }

    /***
     * 查询账单余额
     * @return
     */
    @GetMapping("getBalance")
    public Object getBalance(){
        return ResultUtil.success(keepDao.getBalance());
    }


    /***
     * 每日支出情况
     * @return
     */
    @GetMapping("getOutlay")
    public Object getOutlay(){
        return ResultUtil.success(keepDao.getOutlay());
    }

    /**
     * 统计账单
     * @param time
     * @return
     */
    @GetMapping("getBillTime")
    public Object getBillTime(String time){
        return ResultUtil.success(keepService.getBillTime(time));
    }
}
