package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
// Request Body 또는 Response Body를 나타낸다.
// 이걸 Http Client들이 그대로 활용 가능
public class ArticleDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String writer;
}
