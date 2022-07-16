package com.bookmore.bavtu.model.api.user;

import com.bookmore.bavtu.validation.ValidPassword;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UserUpdateRequest {

    @NotEmpty
    private String id;

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    @ValidPassword
    private String newPassword;

}
