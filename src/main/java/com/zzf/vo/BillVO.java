package com.zzf.vo;

import com.zzf.config.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillVO {
    private BigDecimal value;
    @Dict(target = "keepTypeDesc",codeType = "KEEP")
    private String keepType;
    @TableField(exist = false)
    private String keepTypeDesc;
    private String remark;
    private String time;
}
