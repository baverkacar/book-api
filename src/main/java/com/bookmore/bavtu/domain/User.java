package com.bookmore.bavtu.domain;

import com.bookmore.bavtu.model.api.book.GoogleBook;
import com.bookmore.bavtu.model.api.book.GoogleBookVolumeInfo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private List<GoogleBookVolumeInfo> library = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    public void addBook(GoogleBookVolumeInfo googleBookVolumeInfo){
        library.add(googleBookVolumeInfo);
    }
}
