package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.owoto.config.Dict;


/**
 * @author zzf
 */
@Data
public class ArticleMini extends BaseEntity {

    /**
     * title
     */
    private String title;

}
