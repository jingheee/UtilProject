package io.lazydog.utilproject;


import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

@SpringBootApplication
public class UtilProjectApplication {

    public static void main(String[] args) throws Exception {
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

// 创建一个SSLContext，使用InsecureTrustManagerFactory来信任所有证书


// 创建一个HttpComponentsClientHttpRequestFactory，使用sslContext和allHostsValid
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(HttpClients.custom()
                .setSSLHostnameVerifier(allHostsValid)
                .build());


        final RestTemplate restTemplate = new RestTemplate(requestFactory);
        final ResponseEntity<String> forEntity = restTemplate.getForEntity("https://localhost:8443/hello", String.class);
        System.out.println(forEntity.getBody());

    }

}
