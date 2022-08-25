package com.bookmore.bavtu.service.impl;


import com.bookmore.bavtu.exception.book.BookNotFoundException;
import com.bookmore.bavtu.model.api.book.GoogleBook;
import com.bookmore.bavtu.model.api.book.GoogleBookAPIResponse;
import com.bookmore.bavtu.service.BooksAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GoogleBooksAPIServiceImpl implements BooksAPIService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * @param name
     * @return GoogleBookAPIRespone - Http Status: 200 (OK)
     */
    @Override
    public List<GoogleBook> get(String name) {
        String url = API_URL + "?q=" +"intitle:" + name + "&maxResults="+ MAX_RESULT +"&printType=books" +  "&key=" + API_KEY;
        ResponseEntity<GoogleBookAPIResponse> googleBookAPIResponse =  restTemplate.getForEntity(url, GoogleBookAPIResponse.class);
        if (googleBookAPIResponse.getBody().getItems() != null){
            return googleBookAPIResponse.getBody().getItems();
        }
        throw new BookNotFoundException("There is no book on Google Books with given name: " + name);
    }
}
