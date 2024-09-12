package com.jenna.pennypilot.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;

    private String username;

    private String email;

    private String password;

    @JsonIgnore
    private String oldPassword;

    private String firstName;

    private String lastName;

    private String createdAt;

    private String updatedAt;

}
