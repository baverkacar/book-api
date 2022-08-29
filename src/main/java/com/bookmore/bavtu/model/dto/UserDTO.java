package com.bookmore.bavtu.model.dto;

import com.bookmore.bavtu.model.api.book.GoogleBookVolumeInfo;
import jdk.dynalink.linker.LinkerServices;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private List<GoogleBookVolumeInfo> library;
}

