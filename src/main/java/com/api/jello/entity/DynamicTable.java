package com.api.jello.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

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
