package com.zzf.mapper;

import com.zzf.entity.ArticleEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zzfn
 * @date 2020-12-10 14:53
 */
@Repository
public interface ArticleESDao extends ElasticsearchRepository<ArticleEs,String> {
}
