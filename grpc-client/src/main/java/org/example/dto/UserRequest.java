package org.example.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserRequest {
    @NotNull
    private Long idx;

    @NotNull
    private String username;

    @Email
    private String email;

    private List<String> roles;
}
