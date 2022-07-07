package com.ncallie.wiki.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ncallie.wiki.sevices.ArticleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wiki")
public class WikiController {

    private final ArticleService articleService;

    public WikiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(path = "/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getArticle(@PathVariable("title") String title, @RequestParam(name = "pretty", required = false) String simple) throws JsonProcessingException {
        return articleService.getOneJson(title, simple);
    }
}
