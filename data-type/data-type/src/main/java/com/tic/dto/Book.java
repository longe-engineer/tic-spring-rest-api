package com.tic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本のEntity.
 * 
 * @author Ciu
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
	"book_id",
	"book_name",
	"status"
})
public class Book {

	/**
	 * 本のID.
	 */
	@JsonProperty("book_id")
	@CsvBindByName(column = "book_id")
	private long bookId;

	/** 本の名前 */
	@JsonProperty("book_name")
	@CsvBindByName(column = "book_name")
	private String bookName;

	/** 貸出状況. */
	@JsonProperty("status")
	@CsvBindByName(column = "status")
	private String status;

	/** ステータスの変更. */
	public void changeStatus() {
		status = STATUS.AVAILABLE.equals(status) ? STATUS.LENDERING : STATUS.AVAILABLE;
	}

	/** 本のステータス. */
	public static final class STATUS {
		/** 利用可能. */
		public static final String AVAILABLE = "available";
		/** 貸出中 */
		public static final String LENDERING = "lendering";
	}

	@Override
	public String toString() {
		return bookId + " - " + bookName + " : " + ("available".equals(status) ? "o" : "x");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		return true;
	}
}
