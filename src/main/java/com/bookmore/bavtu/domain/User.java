package com.bookmore.bavtu.domain;


import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
public class User {
    @Id
    private String id;

    private String username;
    private String password;
    private String email;
    private List<Book> library;
}
