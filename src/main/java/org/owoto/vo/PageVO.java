package org.owoto.vo;

import lombok.Data;

@Data
public class PageVO {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private String field;
    private String order;
}
