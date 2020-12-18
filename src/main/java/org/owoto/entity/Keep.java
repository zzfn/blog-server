package org.owoto.entity;

import org.owoto.config.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
