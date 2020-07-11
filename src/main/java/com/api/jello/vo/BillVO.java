package com.api.jello.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillVO {
    private BigDecimal value;
    private String keepType;
    private String time;
}
