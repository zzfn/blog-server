package com.zzf.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
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
    @Value("${spring.elasticsearch.rest.username}")
    private String username;
    @Value("${spring.elasticsearch.rest.password}")
    private String password;

    @NotNull
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        List<HttpHost> httpHostsList = new ArrayList<>();
        httpHostsList.add(new HttpHost(url,Integer.parseInt(port)));
        HttpHost[] httpHostsArray = new HttpHost[httpHostsList.size()];
        httpHostsArray = httpHostsList.toArray(httpHostsArray);
        RestClientBuilder builder = RestClient.builder(httpHostsArray);
        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setSoKeepAlive(true).build()));
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(builder);
    }
    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate()
    {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
