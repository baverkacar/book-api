package com.bookmore.bavtu.model.api.book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoogleBook {
    public String id;
    public GoogleBookVolumeInfo volumeInfo;
}