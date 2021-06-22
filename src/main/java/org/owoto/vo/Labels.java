package org.owoto.vo;

import lombok.Data;
import org.owoto.config.Dict;

@Data
public class Labels {
    private String categoryDesc;

    @Dict(target = "categoryDesc",codeType = "FAVORITE")
    private String CATEGORY;
    private Integer count;
}
