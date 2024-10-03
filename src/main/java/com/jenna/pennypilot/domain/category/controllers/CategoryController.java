package com.jenna.pennypilot.domain.category.controllers;

import com.jenna.pennypilot.domain.category.dtos.CategoryDTO;
import com.jenna.pennypilot.domain.category.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "사용자의 카테고리(분류) 전체 조회", description = "userId 기준")
    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllCtgsByUserId(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(categoryService.getAllCtgsByUserId(userId));
    }

    @Operation(summary = "카테고리(분류) 정보 상세 조회", description = "하나의 카테고리 정보 by id")
    @GetMapping("/detail/{ctgId}")
    public ResponseEntity<?> getCtgDetailById(@PathVariable("ctgId") int ctgId) {
        return ResponseEntity.ok(categoryService.getCtgDetailById(ctgId));
    }

    @Operation(summary = "카테고리(분류) 정보 추가")
    @PostMapping("")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @Operation(summary = "카테고리(분류) 정보 업데이트", description = "카테고리 id를 기준으로, 카테고리명 변경 가능")
    @PutMapping("")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO category) {
        categoryService.updateCategory(category);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "카테고리(분류) 정보 삭제", description = "카테고리 id 기준")
    @DeleteMapping("/{userId}/{ctgId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("userId") int userId, @PathVariable("ctgId") int ctgId) {
        categoryService.deleteCategoryById(userId, ctgId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "카테고리(분류) 순번 업데이트", description = "필요 파라미터: id / userId / 새 seq, 순번 변경 && 수정 사항에 맞춰 다른 카테고리 순변 일괄 변경")
    @PutMapping("/seq/{ctgId}")
    public ResponseEntity<?> updateCtgSeq(@PathVariable("ctgId") int ctgId, @RequestBody CategoryDTO category) {
        category.setId(ctgId);
        categoryService.updateCtgSeq(category);
        return ResponseEntity.ok().build();
    }
}
