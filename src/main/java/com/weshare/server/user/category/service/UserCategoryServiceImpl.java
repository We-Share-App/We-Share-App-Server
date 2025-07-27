package com.weshare.server.user.category.service;

import com.weshare.server.category.entity.Category;
import com.weshare.server.category.exception.CategoryException;
import com.weshare.server.category.exception.CategoryExceptions;
import com.weshare.server.category.repository.CategoryRepository;
import com.weshare.server.user.category.entity.UserCategory;
import com.weshare.server.user.category.repository.UserCategoryRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCategoryServiceImpl implements UserCategoryService{
    private final UserCategoryRepository userCategoryRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public List<Long> createUserCategory(List<Long> categoryIdList, CustomOAuth2User principal) {
        // 유저 찾기
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->new UserException(UserExceptions.USER_NOT_FOUND));

        // category 찾기
        List<Category> categoryList = new ArrayList<>();
        for(Long categoryId : categoryIdList){
            Category res = categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryException(CategoryExceptions.NOT_EXIST_CATEGORY_ID));
            categoryList.add(res);
        }

        // user_category 인스턴스 생성 및 저장
        List<Long> userCategoryIdList = new ArrayList<>();
        for(Category category : categoryList){
            //중복 저장 방지
            if(userCategoryRepository.findByUserAndCategory(user,category).isPresent()){
                continue;
            }
            UserCategory userCategory = UserCategory.builder()
                    .user(user)
                    .category(category)
                    .build();
            UserCategory savedUserCategory = userCategoryRepository.save(userCategory);
            userCategoryIdList.add(savedUserCategory.getId());
        }

        return userCategoryIdList;
    }
}
