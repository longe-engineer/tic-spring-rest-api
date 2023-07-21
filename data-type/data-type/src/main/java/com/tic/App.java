package com.tic;

import com.tic.service.BookCSVService;
import com.tic.service.BookJSONService;

@SuppressWarnings("unused")
public class App {
	private static final BookJSONService jsonService = new BookJSONService();
	private static final BookCSVService csvService = new BookCSVService();

    public static void main(String[] args) throws Exception {

        csvService.changeBookStatus(1,2,3,4,5,6,7,8,9,10);
    }
}
