package org.owoto.service;


import org.owoto.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ArticleService extends IService<Article> {
    Article getArticle(String id);
}
