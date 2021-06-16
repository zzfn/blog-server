package org.owoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.owoto.entity.Discuss;
import org.owoto.service.DiscussService;
import org.owoto.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论表(Discuss)表控制层
 *
 * @author makejava
 * @since 2021-06-16 16:45:18
 */
@RestController
@RequestMapping("discuss")
public class DiscussController {
    /**
     * 服务对象
     */
    @Resource
    private DiscussService discussService;

    @PostMapping("non/save")
    public Object saveOne(@RequestBody Discuss discuss) {
        return ResultUtil.success(this.discussService.save(discuss));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("non/select")
    public Object selectOne(String id) {
        QueryWrapper<Discuss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ARTICLE_ID", id).orderByDesc("CREATE_TIME");
        return ResultUtil.success(this.discussService.list(queryWrapper));
    }

}
