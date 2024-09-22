package com.jenna.pennypilot.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private int id;

    private int userId;

    private String ctgNm;

    private int seq;

    private int oldSeq;     // seq 수정 시 사용

    private String createdAt;

    private String updatedAt;

}
