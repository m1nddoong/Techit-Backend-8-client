package com.example.api.client;


import com.example.api.dto.ArticleDto;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleTemplateClient {
    private final RestTemplate restTemplate;

    public ArticleDto create(ArticleDto dto) {
        // postForObject : 객체르 받기 위해 POST 요청을 한다.
        ArticleDto response = restTemplate.postForObject(
                // 요청 url
                "/articles",
                // Request Body
                dto,
                // Response Body의 자료형
                ArticleDto.class
        );
        log.info("response: {}", response);

        // postForEntity: ResponseEntity를 받기위해 POST 요청을 한다.
        ResponseEntity<ArticleDto> responseEntity = restTemplate.postForEntity(
                // 요청 url
                "/articles",
                // Request Body
                dto,
                // Response Body의 자료형
                ArticleDto.class
        );
        log.info("responseEntity: {}", responseEntity);
        log.info("status code: {}", responseEntity.getStatusCode());
        log.info("headers: {}", responseEntity.getHeaders());
        response = responseEntity.getBody();
        return response;
    }

    // GET
    public ArticleDto readOne(Long id) {
        // getForObject: 객체를 받기위해 GET 요청을 한다.
        ArticleDto response = restTemplate.getForObject(
                String.format("/articles/%d", id), ArticleDto.class
        );
        log.info("response: {}", response);

        // with uriVariable
        response = restTemplate.getForObject(
                "/articles/{id}", ArticleDto.class, id
        );
        log.info("response: {}", response);

        // getForEntity: ResponseEntity를 받기위해 GET 요청을 한다.
        ResponseEntity<ArticleDto> responseEntity = restTemplate.getForEntity(
                String.format("/articles/%d", id), ArticleDto.class
        );
        log.info("responseEntity: {}", responseEntity);
        log.info("status code: {}", responseEntity.getStatusCode());

        // getForObject
        Object responseObject = restTemplate.getForObject(
                String.format("/articles/%d", id), ArticleDto.class
        );

        return response;
    }

    public List<ArticleDto> readAll() {
        // getForObject
        ArticleDto[] response = restTemplate.getForObject(
                "/articles", ArticleDto[].class
        );
        log.info("response type: {}", response.getClass());

        ResponseEntity<ArticleDto[]> responseEntity = restTemplate.getForEntity(
                "/articles", ArticleDto[].class
        );
        log.info("responseEntity: {}", responseEntity);
        log.info("status code: {}", responseEntity.getStatusCode());

        // exchange: 일반적인 상황에서 HTTP 요청의 모든 것 (메서드, 헤더, 바디 등등 .. )_을
        // 묘사하여 요청하기 위한 메서드
        // + ParameterizedTypeReference<T> 를 사용하면 List로 반환한다.
        ResponseEntity<List<ArticleDto>> reesponseListEntity = restTemplate.exchange(
                "/articles",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ArticleDto>>() {}

        );
        log.info("response parameterized: {}", responseEntity.getBody().getClass());

        // getForObject - Object
        Object responseObject = restTemplate.getForObject(
                "/articles", Object.class
        );
        log.info("response object: {}", responseObject.getClass());


        // URL 인자 대체하기, 가변갯수인자
        Object responsePage = restTemplate.getForObject(
                "/articles/paged?page={page}&limit={limit}",
                Object.class,
                0,
                10
        );
        log.info("response object page: {}", responsePage);

        // URL 인자 대체하기, Map<String, Object>
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("page", 0);
        uriVariables.put("limit", 5);
        responsePage = restTemplate.getForObject(
                "/articles/paged?page={page}&limit={limit}",
                Object.class,
                uriVariables
        );
        log.info("response object page: {}", responsePage);

        log.info(UriComponentsBuilder.fromUriString("/articles/paged")
                .queryParam("page", 0)
                .queryParam("limit", 2)
                .toUriString());

        // /test?foo=%25%26 -> /test?foo=%2525%2526
        log.info(UriComponentsBuilder.fromUriString("/test")
                .queryParam("foo", "%&")
                .toUriString());
        // /test?foo=%& -> /test?foo=%25%26
        log.info(UriComponentsBuilder.fromUriString("/test")
                .queryParam("foo", "%&")
                .build(false)
                .toUriString());

        // /search?q=&&page=10 실제로는 얘를 의미하지만
        // /search?q=%26%page=10 이 방법으로 &를 인코딩 하겠다.

        return Arrays.stream(response)
                .toList();

    }

    // PUT
    public ArticleDto update(Long id, ArticleDto dto) {
        // put : PUT 요청을 보낸다.
        restTemplate.put(String.format("/articles/%d", id), dto);

        // exchange
        ResponseEntity<ArticleDto> responseEntity = restTemplate.exchange (
                String.format("/articles/%d", id),
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                ArticleDto.class
        );
        log.info("status code: {}", responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    // DELETE
    public void delete(Long id) {
        // restTemplate.delete(String.format("/articles/%d", id));
        // exchange
        // ResponseEntity<Void> : Response Body 가 비어있는 응답
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                String.format("/articles/%d", id),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        log.info("status code: {}", responseEntity.getStatusCode());
    }

}

































