package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenchen
 */
@Data
@TableName(value = "DYNAMIC_FORM")
public class DynamicForm extends BaseEntity {
    /**
     * 字段id
     */
    private String propertyId;
    /**
     * 记录id
     */
    private String recordId;
    /**
     * 字段值
     */
    private String recordValue;

    @TableField(exist = false)
    private DynamicProperty dynamicProperty;
}
