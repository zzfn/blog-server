package com.zzf.vo;

import com.zzf.config.Dict;
import lombok.Data;

/**
 * @author chenchen
 */
@Data
public class Labels {
    private String categoryDesc;

    @Dict(target = "categoryDesc",codeType = "FAVORITE")
    private String category;
    private Integer count;
}
