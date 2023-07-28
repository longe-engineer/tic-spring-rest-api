package com.tic.restapi.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本の集計結果Entity.
 * 
 * @author Ciu
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
	"book_name",
	"available_count",
	"ids"
})
public class BookAggregation {

	/** 本の名前 */
	@JsonProperty("book_name")
	@CsvBindByName(column = "book_name")
	private String name;

	/**
	 * 貸し出し可能数.
	 */
	@JsonProperty("available_count")
	@CsvBindByName(column = "available_count")
	private long availableCount;

	/**
	 * 本のID.
	 */
	@JsonProperty("book_id")
	@CsvBindByName(column = "book_id")
	private List<Integer> ids = new ArrayList<>();

	public void aggregate(List<Book> books) {
		books.stream()
				// 名前が同じで利用可能なもののみ残す
				.filter(book -> Objects.equals(this.name, book.getName()) && book.isAvailable())
				// 利用可能数の増加と、idの追加
				.forEach(book -> {
					this.availableCount++;
					this.ids.add(Integer.valueOf((int) book.getId()));
				});

		for (Book book : books) {
			// 名前が同じが違う、もしくは利用不可能な場合、return
			if (!(Objects.equals(this.name, book.getName()) && book.isAvailable()))
				return;
			// 利用可能数の増加と、idの追加
			this.availableCount++;
			this.ids.add(Integer.valueOf((int) book.getId()));
		}
	}

	public BookAggregation(String name) {
		this.name = name;
	}
}
