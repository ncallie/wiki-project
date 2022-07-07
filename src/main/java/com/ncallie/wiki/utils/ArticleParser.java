package com.ncallie.wiki.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncallie.wiki.models.Article;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ArticleParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    private List<Article> parseByJavaFile(File file) {
        List<Article> articles = new ArrayList<>();

        try (final FileInputStream fileInputStream = FileUtils.openInputStream(file)) {
            final LineIterator iter = IOUtils.lineIterator(fileInputStream, StandardCharsets.UTF_8);
            while (iter.hasNext()) {
                final String line = iter.nextLine();
                final Article article = mapper.readValue(line, Article.class);
                if (article.getTitle() != null)
                    articles.add(article);
            }
            return articles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<Article> parseByDirectory(String dirPath) {
        try {
            return Files.walk(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .map(this::parseByJavaFile)
                    .flatMap(List::stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
