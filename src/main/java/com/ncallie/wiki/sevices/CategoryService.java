package com.ncallie.wiki.sevices;

import com.ncallie.wiki.entities.ArticleEntity;
import com.ncallie.wiki.entities.Category;
import com.ncallie.wiki.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Map<String, Integer> getAllStatisticsByCategory() {
        List<Category> categories = getAll();
        Map<String, Integer> statistics = new HashMap<>();
        for (Category category : categories) {
            String categoryName = category.getCategoryName();
            statistics.put(categoryName, category.getArticles().size());
        }
        return statistics;
    }
}
