package com.weshare.server.user.category.repository;

import com.weshare.server.category.entity.Category;
import com.weshare.server.user.category.entity.UserCategory;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {
    Optional<UserCategory>findByUserAndCategory(User user, Category category);
}
