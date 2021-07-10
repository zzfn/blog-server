package com.zzf.vo;

import lombok.Data;

@Data
public class PageVO {
    private Integer current = 1;
    private Integer pageSize = 10;
    private String field;
    private String order;
}
