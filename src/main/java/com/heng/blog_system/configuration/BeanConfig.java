package com.heng.blog_system.configuration;

import com.heng.util.ESClient;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Value("${es.host}")
    private String ESHost;

    @Value("${es.port}")
    private int ESPort;

    @Value("${es.protocol}")
    private String ESProtocol;


    @Bean
    public ESClient getESClient(){
        HttpHost host = new HttpHost(ESHost,ESPort,ESProtocol);
        ESClient client = ESClient.ESClientBuilder.init(host);
        return client;
    }
}
