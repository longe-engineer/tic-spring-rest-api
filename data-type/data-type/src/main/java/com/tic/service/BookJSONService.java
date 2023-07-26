package com.tic.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tic.dto.Book;

/**
 * 本とJsonをやりとりするService.<br>
 * 
 * @author Ciu
 * @version 1.0.0
 */
public class BookJSONService {

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

	/**
	 * Q3 解答
	 * @param availableBook
	 * @return
	 * @throws JsonProcessingException
	 */
	public String showAggregation(Map<String, Integer> availableBook) throws JsonProcessingException {
		// ObjectMapperを使用すると勝手にいい感じにしてくれる
		// 期待通りかどうかは必ず確認すること
		return new ObjectMapper().writeValueAsString(availableBook);
	}
}
