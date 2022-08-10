package com.bookmore.bavtu.model.api.user;

import com.bookmore.bavtu.validation.ValidPassword;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UpdateUserPasswordRequest {

    @NotEmpty
    private String id;

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    @ValidPassword
    private String newPassword;

}
