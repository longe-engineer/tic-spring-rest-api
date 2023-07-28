package com.tic.restapi.service;

import java.io.IOException;
import java.util.List;

import com.tic.restapi.dto.Book;
import com.tic.restapi.dto.BookAggregation;

public interface BookService {

    List<Book> getAllBook() throws IOException;

    boolean addBook(Book book) throws IOException;
    
    List<BookAggregation> aggregate(List<Book> books);

}
