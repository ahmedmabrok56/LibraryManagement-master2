package com.dailycodework.lib.repository;

import com.dailycodework.lib.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {
}
