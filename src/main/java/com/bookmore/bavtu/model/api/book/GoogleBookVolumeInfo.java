package com.bookmore.bavtu.model.api.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // bunun sayesinde null value'ları alabiliyoruz. Hata yollamıyor google api.
@Data
public class GoogleBookVolumeInfo {
    public String title;
    public String[] authors;
    public String publisher;
    public String publishedDate;
    public int pageCount;
    public String mainCategory;
    public ImageLinks imageLinks;
}
