package com.tic.restapi.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tic.restapi.dto.Book;
import com.tic.restapi.dto.BookAggregation;
import com.tic.restapi.service.BookService;
import com.tic.restapi.service.logic.BookCSVLogic;

@Service
public class BookServiceImpl implements BookService {

	private final BookCSVLogic csvLogic;

	public BookServiceImpl(BookCSVLogic csvLogic) {
		this.csvLogic = csvLogic;
	}

	@Override
	public List<Book> getAllBook() throws IOException {
		return csvLogic.getAllBooks();
	}

	@Override
	public boolean addBook(Book book) {
		boolean result;
		try {
			csvLogic.addBook(book);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public List<BookAggregation> aggregate(List<Book> books) {

		// 最終的に返却するリスト
		List<BookAggregation> bookAggregationList = new ArrayList<>();
		// このfor文は本の名前のリストを作成しているだけ、実際の件数などはのちの処理で取得
		for (Book book : books) {
			int bookAlreadyCount = (int) bookAggregationList
					.stream()
					.map(ba -> ba.getName())
					.filter(name -> name.equals(book.getName()))
					.count();
			// すでに登録されている本の名前の場合、登録しない
			if (bookAlreadyCount >= 1) {
				continue;
			}
			bookAggregationList.add(new BookAggregation(book.getName()));
		}
		// 返却するDTOの情報をセットする
		bookAggregationList.stream().forEach(bookAggregation -> bookAggregation.aggregate(books));

		return bookAggregationList;
	}
}
