package com.example.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Configuration
public class WebClientConfig {
    @Bean
    // WebClient.Builder를 활용해 전체 서비스에서 사용할
    // 기본 설정을 갖춘 WebClient  Bean으로 등록 가능
    public WebClient defaultWebTemplate() {
        // 그냥 새로 생성해서 사용할 수도 있음
        // WebCleint webClient = WebClient.create();

        // RestTMeplate에 비해 선언형 (함수형) 구조르 가지낟.
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader("test", "foo")
                .defaultRequest(request ->
                        request.header("test", "bar"))
                .defaultStatusHandler(
                        HttpStatusCode::isError,
                        response -> {
                            throw new ResponseStatusException(response.statusCode());
                        }
                )
                .build();
    }
}
