package com.ncallie.wiki.sevices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncallie.wiki.entities.ArticleEntity;
import com.ncallie.wiki.entities.Category;
import com.ncallie.wiki.models.Article;
import com.ncallie.wiki.repositories.ArticleRepository;
import com.ncallie.wiki.repositories.CategoryRepository;
import com.ncallie.wiki.utils.ArticleParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class ArticleService {

    @Value("${upload.path}")
    private String uploadPath;
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleParser parser;

    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository, ArticleParser parser) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.parser = parser;
    }

    public void saveAll(MultipartFile jsonDumpWiki) throws IOException {
        if (!jsonDumpWiki.isEmpty() && jsonDumpWiki != null) {
            if (!jsonDumpWiki.getOriginalFilename().isEmpty()) {
                List<Article> articles = convertJsonToObj(jsonDumpWiki);
                List<ArticleEntity> articleEntities = new ArrayList<>();
                for (Article article : articles) {
                    ArticleEntity articleEntity = new ArticleEntity();
                    myCopyProperties(article, articleEntity);
                    articleEntities.add(articleEntity);
                }
                articleRepository.saveAll(articleEntities);
            }
        }
    }

    private boolean searchCat(List<Category> allCategoryInDB, String name, ArticleEntity target) {
        for (Category categoryBD : allCategoryInDB) {
            if (categoryBD.getCategoryName().equals(name)) {
                categoryBD.getArticles().add(target);
                target.getCategory().add(categoryBD);
                return true;
            }
        }
        return false;
    }

    private void myCopyProperties(Article source, ArticleEntity target) {
        if (source.getAuxiliary_text() != null) source.getAuxiliary_text().removeIf(String::isEmpty);
        BeanUtils.copyProperties(source, target);
        List<String> category_src = source.getCategory();
        List<Category> allCategoryInDB = categoryRepository.findAll();
        for (String catName : category_src) {
            if (searchCat(allCategoryInDB, catName, target))
                ;
            else {
                Category newCategory = new Category();
                newCategory.setCategoryName(catName);
                newCategory.getArticles().add(target);
                target.getCategory().add(newCategory);
                allCategoryInDB.add(newCategory);
                categoryRepository.save(newCategory);
            }
        }
    }

    private List<Article> convertJsonToObj(MultipartFile jsonDumpWiki) throws IOException {
        File upDir = new File(uploadPath);
        if (!upDir.exists()) upDir.mkdir();
        String uuidFile = UUID.randomUUID().toString();
        String filename = uploadPath + "/" + uuidFile;
        jsonDumpWiki.transferTo(new File(filename));
        Stream<Article> articleStream = parser.parseByDirectory(filename);
        return articleStream.toList();
    }

    public String getOneJson(String title, String simple) throws JsonProcessingException {
        List<ArticleEntity> fromBD = articleRepository.findArticleEntityByTitleIgnoreCase(title);
        if (fromBD.isEmpty())
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        if (simple != null) {
            for (ArticleEntity one : fromBD) {
                stringBuilder.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(one)).append("\n");
            }
        } else {
            for (ArticleEntity one : fromBD) {
                stringBuilder.append(objectMapper.writeValueAsString(one));
            }
        }
        return stringBuilder.toString();
    }

    public ArticleEntity getRandArticle() {
        return articleRepository.findArticleEntitiesByRandom();
    }
}
