package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
