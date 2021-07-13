package com.zzf.entity;

import com.zzf.config.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zzf
 */
@Data
public class Article extends BaseEntity implements Serializable {

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

    @TableField(exist = false)
    private Long starCount;

    private Integer orderNum;

    private Boolean isRelease;

    @Dict(target = "tagDesc",codeType = "TAG")
    private String tag;

    @TableField(exist = false)
    private String tagDesc;

}
