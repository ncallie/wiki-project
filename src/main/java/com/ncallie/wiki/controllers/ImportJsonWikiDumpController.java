package com.ncallie.wiki.controllers;

import com.ncallie.wiki.sevices.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Controller
public class ImportJsonWikiDumpController {

    private final ArticleService articleService;

    public ImportJsonWikiDumpController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public String addDumpPage() {
        return "dumpPage";
    }

    @PostMapping()
    public String importDump(@RequestParam("file") MultipartFile jsonDumpWiki) throws IOException {
        articleService.saveAll(jsonDumpWiki);
        return "dumpPage";
    }
}
