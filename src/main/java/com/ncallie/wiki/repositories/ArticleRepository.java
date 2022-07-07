package com.ncallie.wiki.repositories;

import com.ncallie.wiki.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    List<ArticleEntity> findArticleEntityByTitleIgnoreCase(String title);
    @Query(
            value = "SELECT * FROM article ORDER by random() LIMIT 1",
            nativeQuery = true)
    ArticleEntity findArticleEntitiesByRandom();
}
