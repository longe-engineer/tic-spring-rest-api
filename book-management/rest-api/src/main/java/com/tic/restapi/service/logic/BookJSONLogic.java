package com.tic.restapi.service.logic;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tic.restapi.dto.Book;

public class BookJSONLogic {
    
    /**
     * 本のリストをJson文字列に変換する.<br>
     * @throws JsonProcessingException
     */
    public String toJson(List<Book> books) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(books);
    }

    /**
     * JSONを本のリストに変換する.<br>
     */
    public List<Book> toBeans(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JavaType booklistType = mapper.getTypeFactory().constructCollectionType(List.class, Book.class);
        List<Book> books = mapper.readValue(jsonString, booklistType);
        books.stream().forEach(System.out::println);
        return books;
    }
}
