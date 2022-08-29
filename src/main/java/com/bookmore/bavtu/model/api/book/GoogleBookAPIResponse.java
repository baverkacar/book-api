package com.bookmore.bavtu.model.api.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleBookAPIResponse {
    public List<GoogleBook> items;

}