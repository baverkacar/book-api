package com.bookmore.bavtu.model.api.user;


import com.bookmore.bavtu.validation.ValidPassword;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UserDeleteRequest {
    @NotEmpty
    private String id;

    @NotEmpty
    private String password;
}
