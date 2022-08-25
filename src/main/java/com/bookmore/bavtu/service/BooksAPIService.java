package com.bookmore.bavtu.service;

import com.bookmore.bavtu.model.api.book.GoogleBook;
import com.bookmore.bavtu.model.api.book.GoogleBookAPIResponse;

import java.util.List;

public interface BooksAPIService {
    String  API_URL = "https://www.googleapis.com/books/v1/volumes";
    String API_KEY = "AIzaSyBrm7H7Q5X3piMnzcDqabLEOarJrcwVV6U";
    int MAX_RESULT = 10;

    public List<GoogleBook> get(String name);

}
