package com.zzf.entity;

import com.zzf.annotation.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author zzf
 */
@Data
public class Article extends BaseEntity implements Serializable {
    /**
     * logo
     */
    private String logo;

    /**
     * title
     */
    private String title;

    /**
     * content
     */
    private String content;


    @TableField(exist = false)
    private Long viewCount;

    private Integer orderNum;

    private Boolean isRelease;

    @Dict(target = "tagDesc",codeType = "TAG")
    private String tag;

    @TableField(exist = false)
    private String tagDesc;

}
