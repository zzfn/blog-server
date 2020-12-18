package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenchen
 */
@Data
@TableName(value = "DYNAMIC_RECORD")
public class DynamicRecord extends BaseEntity {
    /**
     * 表名
     */
    private String tableId;
}
