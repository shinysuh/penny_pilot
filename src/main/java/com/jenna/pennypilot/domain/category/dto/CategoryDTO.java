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

    private String createdAt;

    private String updatedAt;

}
