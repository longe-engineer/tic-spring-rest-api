package com.tic.restapi.service.logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tic.restapi.dto.History;

@Service
public class HistoryCSVLogic {

	public static final String CSV_PATH = "../../data/history.csv";

	public boolean addHistory(long bookId, String requestType) throws Exception {
		History history = new History(LocalDate.now(), bookId, requestType);

		List<History> histories = new CsvToBeanBuilder<History>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(History.class).build().parse();
		histories.add(history);

		writeBeanToCsv(histories);

		return true;
	}

	/**
	 * 申請履歴のcsvへの書き出し.
	 * 
	 * @param histories
	 * @throws Exception
	 */
	private void writeBeanToCsv(List<History> histories) throws Exception {
		Writer writer = new FileWriter(CSV_PATH);
		StatefulBeanToCsv<History> beanToCsv = new StatefulBeanToCsvBuilder<History>(writer).build();
		beanToCsv.write(histories);
		writer.close();
	}

	public List<History> getHistory(long rowNum) throws Exception {
		List<History> history = new CsvToBeanBuilder<History>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(History.class).build().parse();
		if (history.size() < rowNum)
			return history;
		history.removeAll(history.subList(0, (int) (history.size() - rowNum)));
		return history;
	}
}
