package com.api.jello.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenchen
 */
@Data
@TableName(value = "DYNAMIC_RECORD")
public class DynamicRecord extends BaseEntity {
    /**
     * 字段id
     */
    private String propertyId;
    /**
     * 字段值
     */
    private String propertyValue;
}
