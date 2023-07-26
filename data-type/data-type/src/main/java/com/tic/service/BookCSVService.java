package com.tic.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tic.dto.Book;
import com.tic.dto.Book.STATUS;

/**
 * 本とCSVをやりとりするクラス.<br>
 * 
 * @author Ciu
 * @version 1.0.0
 */
public class BookCSVService {

	public static final String CSV_PATH = "../../data/books.csv";

	/**
	 * 本を全て取得.<br>
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<Book> getAllBooks() throws IOException {

		List<Book> books = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(Book.class).build().parse();
		return books;
	}

	/**
	 * 抽出条件を指定し、本のデータを取得.<br>
	 * 
	 * @param targetColumn
	 * @param targetValue
	 * @return
	 * @throws Exception
	 */
	public List<Book> getBookWith(String targetColumn, String targetValue) throws Exception {

		Field targetField = Book.class.getDeclaredField(targetColumn);

		List<Book> books = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(Book.class)
				.build()
				.parse() // ここまででcsvの読み取りを行う
				.stream()
				.filter(book ->
				// filterメソッドの「->の後ろ」には、boolean を返す処理を記入する。
				{
					/* 指定されたフィールドの値が、検索条件から始まる場合に取得 */
					String value;
					boolean isAccessable = targetField.canAccess(book);
					try {
						targetField.setAccessible(true);
						value = (String) targetField.get(book);
					} catch (Exception e) {
						e.printStackTrace();
						value = "";
					} finally {
						targetField.setAccessible(isAccessable);
					}
					return value.startsWith(targetValue);
				})
				// リターンした値のListを作成する（今回の場合はBook）
				.toList();

		return books;
	}

	/**
	 * 本を追加する.<br>
	 * 本のIDは強制的に連番となる。
	 * 
	 * @param book
	 * @throws Exception
	 */
	public void addBook(Book book) throws Exception {

		List<Book> books = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(Book.class).build().parse();
		book.setBookId(books.size() + 1);
		books.add(book);

		writeBeanToCsv(books);
	}

	/**
	 * 本のステータスを変更する.<br>
	 * 
	 * @param targetIds
	 * @throws Exception
	 */
	public void changeBookStatus(long... targetIds) throws Exception {
		List<Book> books = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(Book.class)
				.build()
				.parse()
				.stream()
				.map(b ->
				// mapメソッドの「->の後ろ」では、とりあえずなんでも良いからreturnする
				{
					for (long targetId : targetIds) {
						if (b.getBookId() == targetId) {
							b.changeStatus();
							break;
						}
					}
					return b;
				})
				// リターンした値のListを作成する（今回の場合はBook）
				.toList();

		writeBeanToCsv(books);
	}

	/**
	 * 本のcsvへの書き出し.
	 * 
	 * @param books
	 * @throws Exception
	 */
	private void writeBeanToCsv(List<Book> books) throws Exception {
		Writer writer = new FileWriter(CSV_PATH);
		StatefulBeanToCsv<Book> beanToCsv = new StatefulBeanToCsvBuilder<Book>(writer).build();
		beanToCsv.write(books);
		writer.close();
	}

	/**
	 * Q1 模範解答.<br>
	 * 本を追加する.<br>
	 * 本のIDは強制的に連番となる。
	 * 
	 * @param newBooks
	 * @throws Exception
	 */
	public void addBooks(List<Book> newBooks) throws Exception {

		List<Book> existsBooks = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(Book.class).build().parse();
		
		// List.stream().forEach(elem -> {}); <=> for (elem : List) {}
		// 要素を取り出して、elemとして{}の中で使用する -> 意味は変わらない
		
		newBooks.stream()
				.forEach(newBook -> {
					// IDを強制的に連番にする
					newBook.setBookId(existsBooks.size() + newBooks.indexOf(newBook) + 1);
				});
//		for (Book newBook : newBooks) {
//			// IDを強制的に連番にする
//			newBook.setBookId(existsBooks.size() + newBooks.indexOf(newBook) + 1);
//		}
		existsBooks.addAll(newBooks);

		writeBeanToCsv(existsBooks);
	}
	/**
	 * Q1 あまり良くない.<br>
	 * 
	 * @param newBooks
	 * @throws Exception
	 */
	public void addBooksBad(List<Book> newBooks) throws Exception {

		List<Book> books = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
				.withType(Book.class).build().parse();

		// 全部追加するなら全部追加するようでメソッドを作りたい
		//  -> 何度もアクセスが発生し理が重くなってしまうため
		for (Book newBook : newBooks) {
			this.addBook(newBook);
		}

		// 以下だと、IDが更新されていない
		books.addAll(newBooks);
		writeBeanToCsv(books);
	}

	/**
	 * Q2
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> aggregateAvailableBooks() throws Exception {
		Map<String, Integer> resultMap = new HashMap<>();
		// 既存の本のリスト
		List<Book> books = this.getAllBooks();

		/* 
		 * 【このメソッドの目標】Mapへ変換
		 *   Key   -> String  : 本の名前
		 *   Value -> Integer : 利用可能な本の数（countif(book.status = "available")
		 */
		// 本の名前のリストを作成する
		// 同じ本の名前を消していく
		List<String> bookNameList = new ArrayList<>();
		for (Book book : books) {
			// すでに登録されている本の名前の場合、登録しない
			if (bookNameList.contains(book.getBookName())) {
				continue;
			}
			bookNameList.add(book.getBookName());
		}

		// 本の名前リストに対応する、利用可能な本の数のリストを作成する
		List<Integer> countList = new ArrayList<>();
		for (String bookName : bookNameList) {
			int count = 0;
			for (Book book : books) {
				// カウントする条件 - 名前が検索一致する かつ 本のステータスがAVAILABLEである
				if (bookName.equals(book.getBookName()) && STATUS.AVAILABLE.equals(book.getStatus())) {
					count++;
				}
			}
			countList.add(count);
		}

		// 本の名前リストと利用可能な本の数のリストの同じインデックスをresultMapに格納する
		for (int i = 0; i < bookNameList.size(); i++) {
			String bookName = bookNameList.get(i);
			int count = countList.get(i);
			resultMap.put(bookName, count);
		}
		return resultMap;
	}
}
