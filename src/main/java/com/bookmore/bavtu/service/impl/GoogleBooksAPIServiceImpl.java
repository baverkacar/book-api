package com.bookmore.bavtu.service.impl;


import com.bookmore.bavtu.model.api.book.GoogleBookVolumeInfo;
import com.bookmore.bavtu.service.GoogleBooksAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j //logger
@RequiredArgsConstructor
public class GoogleBooksAPIServiceImpl implements GoogleBooksAPIService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public ResponseEntity<GoogleBookVolumeInfo[]> get(String name) {
        String url = API_URL + "?q=" +"intitle:" + name + "&printType=books" +  "&key=" + API_KEY;
        System.out.println(url);
        ResponseEntity<String> response =  restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
        GoogleBookVolumeInfo[] books = jsonToGoogleBook(responseBody);
        return new ResponseEntity(books, HttpStatus.OK);
    }

    private GoogleBookVolumeInfo[] jsonToGoogleBook(String json){

        JSONObject GoogleBookResponse = new JSONObject(json);
        JSONArray items = GoogleBookResponse.getJSONArray("items");
        GoogleBookVolumeInfo[] books = new GoogleBookVolumeInfo[items.length()];

        for (int index = 0; index < books.length; index++){
            if(index < MAX_RESULT){
                JSONObject volumeInfo = items.getJSONObject(index).getJSONObject("volumeInfo");
                GoogleBookVolumeInfo googleBookVolumeInfo = GoogleBookVolumeInfo.builder()
                        .title(volumeInfo.getString("title"))
                        .author(volumeInfo.getJSONArray("authors").getString(0))
                        .publishedDate(volumeInfo.getString("publishedDate"))
                        .build();
                books[index] = googleBookVolumeInfo;
            }else break;
        }

        return books;
    }
}
