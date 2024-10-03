package com.jenna.pennypilot.domain.category.services;

import com.jenna.pennypilot.domain.category.dtos.CategoryDTO;
import com.jenna.pennypilot.domain.user.dtos.UserDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCtgsByUserId(int userId);

    CategoryDTO getCtgDetailById(int id);

    CategoryDTO addCategory(CategoryDTO category);

    void updateCategory(CategoryDTO category);

    void deleteCategoryById(int userId, int ctgId);

    void updateCtgSeq(CategoryDTO category);

    void addBasicCategories(UserDTO user);

}
