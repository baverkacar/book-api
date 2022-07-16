package com.bookmore.bavtu.controller;

import com.bookmore.bavtu.model.api.book.GoogleBookVolumeInfo;
import com.bookmore.bavtu.service.impl.GoogleBooksAPIServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wrapper")
@Slf4j
@RequiredArgsConstructor
public class GoogleBooksAPIController {

    private final GoogleBooksAPIServiceImpl googleBooksAPIService;


    @GetMapping("/{name}")
    public ResponseEntity<GoogleBookVolumeInfo[]> get(@PathVariable String name){
        return googleBooksAPIService.get(name);
    }
}
