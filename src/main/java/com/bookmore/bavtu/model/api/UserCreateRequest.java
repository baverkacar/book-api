package com.bookmore.bavtu.model.api;


import com.bookmore.bavtu.validation.ValidPassword;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UserCreateRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @ValidPassword
    private String password;

}
