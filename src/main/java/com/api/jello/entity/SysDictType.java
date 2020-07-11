package com.api.jello.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * DICT_TYPE(SysDictType)实体类
 *
 * @author nanaouyang
 * @since 2020-03-27 21:45:29
 */
@Data
@TableName(value = "SYS_DICT_TYPE")
public class SysDictType extends BaseEntity   {
    /**
    * 编码
    */
    private String code;
    /**
    * 类型
    */
    private String name;
}