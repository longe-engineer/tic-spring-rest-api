package com.tic.restapi.service.impl;

import org.springframework.stereotype.Service;

import com.tic.restapi.dto.Book;
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
public class RequestServiceImpl implements RequestService{

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
    public boolean borrow(long id) throws Exception {
        try {
            bookCsvLogic.changeBookStatus(id);
            historyCsvLogic.addHistory(id, RequestType.BORROW);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    @Override
    public boolean returnOf(long id) throws Exception {
        try {
            bookCsvLogic.changeBookStatus(id);
            historyCsvLogic.addHistory(id, RequestType.RETURN);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
