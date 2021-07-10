package com.zzf.vo;

import com.zzf.config.Dict;
import lombok.Data;

@Data
public class Tags {
    private String tag;

    @Dict(target = "tag",codeType = "TAG")
    private String code;
    private Integer count;
}
