package com.bookmore.bavtu.model.api.user;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class DeleteUserRequest {
    @NotEmpty
    private String id;

    @NotEmpty
    private String password;
}
