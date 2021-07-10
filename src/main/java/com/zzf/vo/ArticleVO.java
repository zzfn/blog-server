package com.zzf.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleVO extends PageVO {
    Boolean isOnlyRelease = true;
    Boolean isRelease;
    String title;
    String tag;
}
