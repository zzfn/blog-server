package org.owoto.controller;

import org.owoto.dao.KeepDao;
import org.owoto.entity.Keep;
import org.owoto.service.KeepService;
import org.owoto.util.ResultUtil;
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
        return ResultUtil.success(keepDao.listKeeps());
//        return ResultUtil.success(keepDao.listKeeps("month","KEEP_2"));
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
