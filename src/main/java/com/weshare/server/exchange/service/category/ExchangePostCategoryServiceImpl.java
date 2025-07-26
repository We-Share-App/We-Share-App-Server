package com.weshare.server.exchange.service.category;

import com.weshare.server.category.entity.Category;
import com.weshare.server.category.exception.CategoryException;
import com.weshare.server.category.exception.CategoryExceptions;
import com.weshare.server.category.repository.CategoryRepository;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostCategory;
import com.weshare.server.exchange.repository.ExchangePostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangePostCategoryServiceImpl implements ExchangePostCategoryService{
    private final CategoryRepository categoryRepository;
    private final ExchangePostCategoryRepository exchangePostCategoryRepository;
    @Override
    @Transactional
    public ExchangePostCategory createExchangePostCategory(Long categoryId, ExchangePost exchangePost) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryException(CategoryExceptions.NOT_EXIST_CATEGORY_ID));
        ExchangePostCategory exchangePostCategory = ExchangePostCategory.builder()
                .category(category)
                .exchangePost(exchangePost)
                .build();
        return exchangePostCategoryRepository.save(exchangePostCategory);
    }
}
