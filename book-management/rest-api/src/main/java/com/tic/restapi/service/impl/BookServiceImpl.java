package com.tic.restapi.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tic.restapi.dto.Book;
import com.tic.restapi.service.BookService;
import com.tic.restapi.service.logic.BookCSVLogic;

@Service
public class BookServiceImpl implements BookService {

    private final BookCSVLogic csvService;

    public BookServiceImpl(BookCSVLogic csvService) {
        this.csvService = csvService;
    }

    @Override
    public List<Book> getAllBook() throws IOException {
        return csvService.getAllBooks();
    }

    @Override
    public boolean addBook(Book book) {
        boolean result;
        try {
            csvService.addBook(book);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    
}
