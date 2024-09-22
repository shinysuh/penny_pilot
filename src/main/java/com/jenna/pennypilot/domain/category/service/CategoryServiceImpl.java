package com.jenna.pennypilot.domain.category.service;

import com.jenna.pennypilot.core.exception.GlobalException;
import com.jenna.pennypilot.domain.category.dto.CategoryDTO;
import com.jenna.pennypilot.domain.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jenna.pennypilot.core.exception.ErrorCode.CATEGORY_ALREADY_EXISTS;
import static com.jenna.pennypilot.core.exception.ErrorCode.CATEGORY_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCtgsByUserId(int userId) {
        // TODO - 사용자 logged in 확인 && userId 일치 확인

        return Optional.ofNullable(categoryMapper.selectAllCtgsByUserId(userId))
                .orElseGet(ArrayList::new);
    }

    @Override
    public CategoryDTO getCtgDetailById(int id) {
        return Optional.of(categoryMapper.selectCtgDetailById(id))
                .orElseThrow(() -> new GlobalException(CATEGORY_NOT_EXISTS));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryDTO addCategory(CategoryDTO category) {
        // 카테고리명 중복 확인
        if (this.checkCtgNm(category)) throw new GlobalException(CATEGORY_ALREADY_EXISTS);

        // 사용자 하위 카테고리 max 순번 + 1
        int maxSeq = categoryMapper.getMaxSeq(category.getUserId());

        if (category.getSeq() != 0 && category.getSeq() < maxSeq) {
            // 이미 존재하는 순번 입력 시, 해당 순번 및 이후 순번 카테고리들 순번 하나씩 뒤로 밀기
            categoryMapper.seqsPlusOneIfCutsies(category);
        } else {
            category.setSeq(maxSeq);
        }

        // 카테고리 정보 추가
        categoryMapper.addCategory(category);
        return category;
    }

    @Override
    public void updateCategory(CategoryDTO category) {
        categoryMapper.updateCategory(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategoryById(int userId, int ctgId) {
        // 뒤 순번 카테고리들 하나씩 순번 올려주기
        categoryMapper.seqsMinusOneIfCtgDeleted(
                CategoryDTO.builder()
                        .userId(userId)
                        .id(ctgId)
                        .build()
        );

        // 카테고리 삭제
        categoryMapper.deleteCategoryById(ctgId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCtgSeq(CategoryDTO category) {
        // 사용자의 다른 카테고리들 순번 일괄 업데이트 (현재 카테고리 업데이트 전)
        categoryMapper.updateOtherCtgsSeqByUser(category);

        // 카테고리 순번 업데이트
        categoryMapper.updateCtgSeq(category);
    }

    private boolean checkCtgNm(CategoryDTO category) {
        return categoryMapper.checkCtgNm(category) > 0;
    }
}
