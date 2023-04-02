package com.sah.shardingSphere.util;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    private volatile static RestTemplate restTemplate;


    public static RestTemplate getRestTemplate() {
        if(restTemplate==null){
            synchronized (RestTemplateUtil.class){
                if(restTemplate==null){
                    restTemplate = new RestTemplate(simpleClientHttpRequestFactory());
                }
            }
        }
        return restTemplate;
    }

    public static ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(360000);
        factory.setConnectTimeout(30000);
        return factory;
    }
}
