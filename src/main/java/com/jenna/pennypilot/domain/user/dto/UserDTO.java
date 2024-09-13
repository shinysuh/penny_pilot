package com.jenna.pennypilot.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User DTO")
public class UserDTO {

    private int id;

    private String username;

    private String email;

    private String password;

    @Schema(description = "old password")
    @JsonIgnore
    private String oldPassword;

    private String firstName;

    private String lastName;

    private String createdAt;

    private String updatedAt;

}
