package org.owoto.service;


import org.owoto.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ArticleService extends IService<Article> {
    Article getArticle(String id);

    List<Article> listArticle(String code);
}
