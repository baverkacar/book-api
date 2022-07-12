package com.bookmore.bavtu.domain;


import lombok.Data;

import java.time.LocalDateTime;


/*
* Inner class for to keep User's library field.
*/
@Data
public class Book {
    private String name;
    private String author;
    private String publisher;
    private String publishedAt;
    private String image;
}
