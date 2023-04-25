package io.lazydog.utilproject.sslUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DefaultClient {

    public static void main(String[] args) {
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<String> forEntity = restTemplate.getForEntity("https://localhost:8443/hello", String.class);
        System.out.println(forEntity.getBody());
    }

}
