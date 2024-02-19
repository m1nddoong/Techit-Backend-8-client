package com.example.api.client;


import com.example.api.dto.ArticleDto;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

// 요청이 어떤식으로 보내져야 하는지가 정의된 인터페이스
// 어떻게 무엇을 가지고 -> 이것은 인테페이스 입장에서는 모름!
// 어떻게를 만들어 줄 수 있는 것이 HttpServiceProxyFactory 이다.
// 요청이 어떤식으로 보내져야하는지가 정의된 인터페이스
@HttpExchange("/articles")
public interface ArticleHttpInterface {
    // CRUD를 할거기 때문에 해당하는 메서드를 다 만든다.

    // @<Method>Exchange 어노테이션은 해당 메서드가 실행될 때
    // HTTP Request의 메서드와 Path를 결정한다.

    // Path, Body, (Query) Parameter를 매개변수로
    // 나타내면 HTTP Request에 포함된다.

    // CREATE
    @PostExchange
    ArticleDto create(
            @RequestBody
            ArticleDto dto
    );

    // READ all
    @GetExchange
    List<ArticleDto> readAll();

    // READ one
    @GetExchange("/{id}")
    ArticleDto readOne(
            @PathVariable("id")
            Long id
    );

    // UPDATE
    @PutExchange("/{id}")
    ArticleDto update(
            @PathVariable("id")
            Long id,
            @RequestBody ArticleDto dto
    );

    // DELETE
    @DeleteExchange("/{id}")
    ArticleDto delete(
            @PathVariable("id")
            Long id
    );
}