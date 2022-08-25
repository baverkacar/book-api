package com.bookmore.bavtu.model.api.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class GoogleBookAPIResponse {
    public List<GoogleBook> items;
}