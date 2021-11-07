package com.zzf.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cc
 */
@Setter
@Getter
public class ArticleVO extends PageVO {
    Boolean isRelease;

    String title;

    String tag;

    String id;
}
