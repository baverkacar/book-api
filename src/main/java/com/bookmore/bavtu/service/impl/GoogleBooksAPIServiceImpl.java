package com.bookmore.bavtu.service.impl;


import com.bookmore.bavtu.model.api.book.GoogleBookAPIResponse;
import com.bookmore.bavtu.model.api.book.GoogleBookVolumeInfo;
import com.bookmore.bavtu.service.BooksAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class GoogleBooksAPIServiceImpl implements BooksAPIService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * @param  name
     * @return GoogleBookAPIRespone - Http Status: 200 (OK)
     */
    @Override
    public ResponseEntity<GoogleBookVolumeInfo[]> get(String name) {
        String url = API_URL + "?q=" +"intitle:" + name + "&maxResults="+ MAX_RESULT +"&printType=books" +  "&key=" + API_KEY;
        ResponseEntity<GoogleBookAPIResponse> googleBookAPIResponse =  restTemplate.getForEntity(url, GoogleBookAPIResponse.class);
        return new ResponseEntity(googleBookAPIResponse, HttpStatus.OK);
    }
}
