package com.bookmore.bavtu.domain;


import lombok.Data;

@Data
public class Book {
    private String name;
    private String author;
    private String publisher;
    private String publishedAt;
    private String image;
}
