package com.bookmore.bavtu.controller;

import com.bookmore.bavtu.exception.book.TooManyRequestException;
import com.bookmore.bavtu.model.api.book.GoogleBook;
import com.bookmore.bavtu.service.impl.GoogleBooksAPIServiceImpl;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/google-books")
@Slf4j
@RequiredArgsConstructor
public class GoogleBooksAPIController {

    private final GoogleBooksAPIServiceImpl googleBooksAPIService;
    private final Bucket bucket;

    public GoogleBooksAPIController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
        this.googleBooksAPIService = new GoogleBooksAPIServiceImpl();
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<GoogleBook>> get(@PathVariable String name){
        if (bucket.tryConsume(1)){
            return new ResponseEntity<>(googleBooksAPIService.get(name), HttpStatus.OK);
        }
        else throw new TooManyRequestException("You made too many requests to the API.");
    }
}