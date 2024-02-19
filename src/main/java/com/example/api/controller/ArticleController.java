package com.example.api.controller;


import com.example.api.client.ArticleHttpInterface;
import com.example.api.client.ArticleService;
import com.example.api.client.ArticleTemplateClient;
import com.example.api.client.ArticleWebClient;
import com.example.api.dto.ArticleDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleTemplateClient templateClient;
    private final ArticleWebClient webClient;
    private final ArticleService service;

    @PostMapping
    public ArticleDto create(
            @RequestBody
            ArticleDto dto
    ) {
        // return templateClient.create(dto);
        return service.create(dto);
    }

    @GetMapping("{id}")
    public ArticleDto readOne(
            @PathVariable("id")
            Long id
    ) {
        // return templateClient.readOne(id);
        return service.readOne(id);
    }

    @GetMapping
    public List<ArticleDto> readAll() {
        return templateClient.readAll();
    }

    @PutMapping("{id}")
    public ArticleDto update(
            @PathVariable("id")
            Long id,
            @RequestBody
            ArticleDto dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  delete (
            @PathVariable("id")
            Long id
    ) {
        service.delete(id);
    }
}
