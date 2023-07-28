package com.tic.restapi.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.tic.restapi.dto.Book;
import com.tic.restapi.dto.Book.STATUS;
import com.tic.restapi.dto.History;
import com.tic.restapi.dto.History.RequestType;
import com.tic.restapi.service.RequestService;
import com.tic.restapi.service.logic.BookCSVLogic;
import com.tic.restapi.service.logic.HistoryCSVLogic;

/**
 * 申請Service.<br>
 * 
 * @author Ciu
 * @version 1.0.0
 */
@Service
public class RequestServiceImpl implements RequestService {

	private final BookCSVLogic bookCsvLogic;

	private final HistoryCSVLogic historyCsvLogic;

	public RequestServiceImpl(BookCSVLogic bookCsvLogic, HistoryCSVLogic historyCsvLogic) {
		this.bookCsvLogic = bookCsvLogic;
		this.historyCsvLogic = historyCsvLogic;
	}

	@Override
	public boolean buy(String bookName) throws Exception {
		Book book = new Book();
		book.setName(bookName);
		bookCsvLogic.addBook(book);
		historyCsvLogic.addHistory(book.getId(), RequestType.BUY);
		return true;
	}

	@Override
	public boolean borrowBook(long id) throws Exception {
		try {
			String status = bookCsvLogic.getAllBooks()
					.stream()
					.filter(b -> Objects.equals(id, b.getId()))
					.findFirst()
					.orElse(new Book() {
						{
							this.setStatus(STATUS.LENDERING);
						}
					})
					.getStatus();
			if (STATUS.LENDERING.equals(status))
				throw new IllegalArgumentException("book : " + id + " is not available.");

			bookCsvLogic.changeBookStatus(id);
			historyCsvLogic.addHistory(id, RequestType.BORROW);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	@Override
	public boolean returnBook(long id) throws Exception {
		try {
			String status = bookCsvLogic.getAllBooks()
					.stream()
					.filter(b -> id == b.getId()).findFirst()
					.orElse(new Book() {
						{
							this.setStatus(STATUS.AVAILABLE);
						}
					})
					.getStatus();
			if (STATUS.AVAILABLE.equals(status))
				throw new IllegalArgumentException("book : " + id + " is not available.");

			bookCsvLogic.changeBookStatus(id);
			historyCsvLogic.addHistory(id, RequestType.RETURN);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	@Override
	public List<History> getHistory(long rowNum) throws Exception {
		List<History> history = historyCsvLogic.getHistory(rowNum);
		return history;
	}
}
