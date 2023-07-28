package com.tic.restapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tic.restapi.dto.Book;
import com.tic.restapi.dto.BookAggregation;
import com.tic.restapi.service.BookService;

/**
 * 本Controller.<br>
 * 
 * @author Ciu
 * @version 1.0.0
 */
@CrossOrigin
@RestController
@RequestMapping("/books")
public class BookContoller {

	private final BookService service;

	public BookContoller(BookService service) {
		this.service = service;
	}

	/**
	 * 本を全て取得.<br>
	 * @return
	 * @throws IOException
	 */
	@GetMapping
	public ResponseEntity<List<Book>> getBooks() throws IOException {
		List<Book> books = service.getAllBook();
		return ResponseEntity.ok(books);
	}

	/**
	 * 本を取得（ただのリスト）.<br>
	 * @return
	 * @throws IOException
	 */
	@GetMapping("simple-list")
	public List<Book> getSimpleListOfBooks() throws IOException {
		List<Book> books = service.getAllBook();
		return books;
	}

	/**
	 * 本の追加.<br>
	 * 
	 * @param book
	 * @return
	 * @throws IOException
	 */
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) throws IOException {
		if (!service.addBook(book)) {
			System.out.println("bad book format");
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * Q1の答え.<br>
	 * データ返却用の新しいDTOを作成する必要がありますね！
	 * これを使わないとだるいことなりそうです。
	 *  
	 * @return
	 * @throws IOException
	 */
	@GetMapping("aggregate")
	public ResponseEntity<List<BookAggregation>> getAggregation() throws IOException {
		// まずは本を取得
		List<Book> books = service.getAllBook();
		// 最終的に返却するリスト
		List<BookAggregation> bookAggregationList = service.aggregate(books);
		
		return ResponseEntity.ok(bookAggregationList);
	}
}
