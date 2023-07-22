package com.tic.restapi.service;

import java.io.IOException;
import java.util.List;

import com.tic.restapi.dto.Book;

public interface BookService {

    List<Book> getAllBook() throws IOException;

    boolean addBook(Book book) throws IOException;

}
