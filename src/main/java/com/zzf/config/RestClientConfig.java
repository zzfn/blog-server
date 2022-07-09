package com.zzf.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cc
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {
    @Value("${spring.elasticsearch.rest.uris}")
    private String url;
    @Value("${spring.elasticsearch.rest.port}")
    private String port;
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        List<HttpHost> httpHostsList = new ArrayList<>();
        httpHostsList.add(new HttpHost(url,Integer.parseInt(port)));
        HttpHost[] httpHostsArray = new HttpHost[httpHostsList.size()];
        httpHostsArray = httpHostsList.toArray(httpHostsArray);
        RestClientBuilder builder = RestClient.builder(httpHostsArray);
        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> 1000 * 60));
        return new RestHighLevelClient(builder);
    }
    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate()
    {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
