package com.jenna.pennypilot.domain.category.mapper;

import com.jenna.pennypilot.domain.category.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryDTO> selectAllCtgsByUserId(int userId);

    CategoryDTO selectCtgDetailById(int id);

    int getMaxSeq(int userId);

    int checkCtgNm(CategoryDTO category);

    CategoryDTO addCategory(CategoryDTO category);

    void updateCategory(CategoryDTO category);

    void deleteCategoryById(int id);

    // 현재 카테고리 순번 업데이트
    void updateCtgSeq(CategoryDTO category);

    // 사용자의 다른 카테고리들 순번 일괄 업데이트
    void updateOtherCtgsSeqByUser(CategoryDTO category);

    // 카테고리 하나 삭제 시, 뒤 순번 카테고리들 하나씩 순번 올려주기
    void seqsMinusOneIfCtgDeleted(CategoryDTO category);

    // 이미 존재하는 순번 입력 시, 해당 순번 및 이후 순번 카테고리들 순번 하나씩 뒤로 밀기
    void seqsPlusOneIfCutsies(CategoryDTO category);
}
