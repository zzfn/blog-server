package com.zzf.vo;

import com.zzf.annotation.Dict;
import com.zzf.entity.Article;
import com.zzf.entity.ArticleMini;
import lombok.Data;

import java.util.List;

/**
 * @author cc
 */
@Data
public class ArticleTagVO {

    @Dict(target = "title",codeType = "TAG")
    private String tagCode;

    private String title;

    private List<ArticleMini> articleList;
}
