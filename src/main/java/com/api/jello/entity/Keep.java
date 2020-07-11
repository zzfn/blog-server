package com.api.jello.entity;

import com.api.jello.config.Dict;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;

@Data
public class Keep extends BaseEntity {

    @Dict(target = "keepTypeDesc",codeType = "KEEP")
    private String keepType;

    @TableField(exist = false)
    private String keepTypeDesc;

    @TableField(exist = false)
    private String day;
    @TableField(exist = false)
    private BigDecimal value;
    private BigDecimal amount;
    private String remark;
}
