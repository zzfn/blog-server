package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * DICT(SysDict)实体类
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:17
 */
@Data
@TableName(value = "SYS_DICT")
public class SysDict extends BaseEntity{
    /**
    * 编码
    */
    private String code;
    /**
    * 字典名
    */
    private String name;
    /**
     * 排序号
     */
    private Integer orderNum;
    /**
    * 类型code
    */
    private String typeCode;
}