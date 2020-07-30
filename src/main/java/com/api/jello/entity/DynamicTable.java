package com.api.jello.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenchen
 */
@Data
@TableName(value = "DYNAMIC_TABLE")
public class DynamicTable extends BaseEntity {
    /**
     * 表名
     */
    private String tableName;

}
