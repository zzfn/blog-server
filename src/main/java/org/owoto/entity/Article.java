package org.owoto.entity;

import org.owoto.config.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;



/**
 * @author zzf
 */
@Data
public class Article extends BaseEntity {

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

    private Integer isRelease;

    @Dict(target = "tagDesc",codeType = "TAG")
    private String tag;

    @TableField(exist = false)
    private String tagDesc;

}
