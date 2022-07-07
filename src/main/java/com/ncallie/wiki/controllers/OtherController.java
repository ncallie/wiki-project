package com.ncallie.wiki.controllers;

import com.ncallie.wiki.entities.ArticleEntity;
import com.ncallie.wiki.entities.Category;
import com.ncallie.wiki.sevices.ArticleService;
import com.ncallie.wiki.sevices.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OtherController {

    private final CategoryService categoryService;
    private final ArticleService articleService;

    public OtherController(CategoryService categoryService, ArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @GetMapping("/catalog")
    public String indexPage(Model model) {
        model.addAttribute("statistics", categoryService.getAllStatisticsByCategory());
        return "catalog";
    }

    @GetMapping("/rand")
    public String randArticlePage(Model model) {
        ArticleEntity randArticle = articleService.getRandArticle();
        if (randArticle == null)
            return "randArticle";
        model.addAttribute("article", randArticle);
        model.addAttribute("categories", randArticle.getCategory());
        return "randArticle";
    }
}
