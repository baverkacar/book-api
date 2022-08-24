package com.bookmore.bavtu.controller;

import com.bookmore.bavtu.model.api.book.GoogleBook;
import com.bookmore.bavtu.model.api.book.GoogleBookAPIResponse;
import com.bookmore.bavtu.model.api.book.GoogleBookVolumeInfo;
import com.bookmore.bavtu.service.impl.GoogleBooksAPIServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/google-books")
@Slf4j
@RequiredArgsConstructor
public class GoogleBooksAPIController {

    private final GoogleBooksAPIServiceImpl googleBooksAPIService;

    @GetMapping("/{name}")
    public ResponseEntity<List<GoogleBook>> get(@PathVariable String name){
        return new ResponseEntity<>(googleBooksAPIService.get(name), HttpStatus.OK);
    }
}