package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zzf.config.Dict;
import lombok.Data;

/**
 * FAVORITE
 * @TableName T_FAVORITE
 */
@TableName(value ="T_FAVORITE")
@Data
public class Favorite extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * logo
     */
    private String img;

    /**
     * 备注
     */
    private String remark;

    /**
     * 链接
     */
    private String link;

    /**
     * 是否发布
     */
    private Boolean isRelease;

    /**
     * 排序号
     */
    private Byte orderNum;

    /**
     * 类目id
     */
    @Dict(target = "categoryDesc",codeType = "FAVORITE")
    private String category;

    @TableField(exist = false)
    private String categoryDesc;
}