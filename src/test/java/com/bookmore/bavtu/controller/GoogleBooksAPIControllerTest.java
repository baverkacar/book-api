package com.bookmore.bavtu.controller;

import com.bookmore.bavtu.exception.book.BookNotFoundException;
import com.bookmore.bavtu.model.api.book.GoogleBook;
import com.bookmore.bavtu.service.impl.GoogleBooksAPIServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GoogleBooksAPIController.class)
@Slf4j
@AutoConfigureWebClient
class GoogleBooksAPIControllerTest {

    @MockBean
    GoogleBooksAPIServiceImpl googleBooksAPIService;

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/api/google-books";
    private final String CONTENT_TYPE = "application/json";

    @Test
    void whenGetGoogleBookWith_validBookName_thenReturn200() throws Exception{
        // given
        String bookName = "Harry Potter";
        GoogleBook googleBook = new GoogleBook();
        List<GoogleBook> googleBooks = new ArrayList<>();
        googleBooks.add(googleBook);

        // when
        when(googleBooksAPIService.get(bookName)).thenReturn(googleBooks);
        ResultActions actions = mockMvc.perform(get(URL + "/" + bookName)
                .contentType(CONTENT_TYPE));

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(googleBooksAPIService, Mockito.times(1)).get(captor.capture());

        actions.andExpect(status().isOk());
        // actions.andDo(print());
    }

    @Test
    void whenGetGoogleBookWith_invalidBookName_thenReturn200() throws Exception{
        // given
        String bookName = "INVALID_BOOK_NAME";

        // when
        when(googleBooksAPIService.get(bookName)).thenThrow(new BookNotFoundException("There is no book on Google Books with given name: " + bookName));
        ResultActions actions = mockMvc.perform(get(URL + "/" + bookName)
                .contentType(CONTENT_TYPE));

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(googleBooksAPIService, Mockito.times(1)).get(captor.capture());

        actions.andExpect(status().isNotFound());
        // actions.andDo(print());
    }
}