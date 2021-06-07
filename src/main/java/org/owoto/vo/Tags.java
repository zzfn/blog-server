package org.owoto.vo;

import lombok.Data;
import org.owoto.config.Dict;

@Data
public class Tags {
    private String tag;

    @Dict(target = "tag",codeType = "TAG")
    private String code;
    private Integer count;
}
