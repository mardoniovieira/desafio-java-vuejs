package com.effecti.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HttpService {

    public HttpResponse executePostWithFormData(String url, String cookie, String host, List<NameValuePair> parameters) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addHeader("Cookie", cookie);
        post.addHeader("Host", host);
        post.setEntity(new UrlEncodedFormEntity(parameters));
        return  client.execute(post);
    }

    public HttpResponse executeGet(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().build()).build();
        return client.execute(new HttpGet(url));
    }
}
