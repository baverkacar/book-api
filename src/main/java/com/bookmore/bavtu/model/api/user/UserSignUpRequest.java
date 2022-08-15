package com.bookmore.bavtu.model.api.user;


import com.bookmore.bavtu.validation.ValidPassword;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UserSignUpRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @ValidPassword
    private String password;

    public UserSignUpRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
