package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.owoto.config.Dict;

/**
 * FAVORITE
 * @TableName T_FAVORITE
 */
@TableName(value ="T_FAVORITE")
@Data
public class Favorite implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

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
    private String isRelease;

    /**
     * 排序号
     */
    private Byte orderNum;

    /**
     * 类目id
     */
    @Dict(target = "categoryDesc",codeType = "FAVORITE")
    private String category;

    private String categoryDesc;
    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标识
     */
    private String isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}