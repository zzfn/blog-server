package com.api.jello.dao;

import com.api.jello.entity.ArticleES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zzfn
 * @date 2020-12-10 14:53
 */
@Repository
public interface ArticleESDao extends CrudRepository<ArticleES,String> {
        List<ArticleES> findDistinctByContentOrTitle(String key1,String key2);
}
