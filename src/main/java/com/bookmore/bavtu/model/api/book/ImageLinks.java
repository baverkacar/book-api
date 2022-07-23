package com.bookmore.bavtu.model.api.book;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageLinks {
    public String smallThumbnail;
    public String thumbnail;
    public String small;
    public String medium;
    public String large;
    public String extraLarge;
}
