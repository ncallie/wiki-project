package com.ncallie.wiki.repositories;

import com.ncallie.wiki.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
