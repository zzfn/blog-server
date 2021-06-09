package org.owoto.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.owoto.util.HttpUtil;
import org.owoto.util.JwtTokenUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author cc
 * @date 2020/3/22 21:45
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "createBy", String.class, HttpUtil.getUserId());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateBy", String.class, HttpUtil.getUserId());
        this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateBy", String.class, HttpUtil.getUserId());
    }
}
