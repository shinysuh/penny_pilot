package com.jenna.pennypilot.domain.category.service;

import com.jenna.pennypilot.domain.category.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCtgsByUserId(int userId);

    CategoryDTO getCtgDetailById(int id);

    CategoryDTO addCategory(CategoryDTO category);

    void updateCategory(CategoryDTO category);

    void deleteCategoryById(int userId, int ctgId);

    void updateCtgSeq(CategoryDTO category);

}
