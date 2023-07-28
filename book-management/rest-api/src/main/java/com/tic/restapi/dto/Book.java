package com.tic.restapi.dto;

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
	private long id;

	/** 本の名前 */
	@JsonProperty("book_name")
	@CsvBindByName(column = "book_name")
	private String name;

	/** 貸出状況. */
	@JsonProperty("status")
	@CsvBindByName(column = "status")
	private String status = STATUS.AVAILABLE;

	/** 貸出状況確認 */
	public boolean isAvailable() {
		return STATUS.AVAILABLE.equals(status);
	}

	/** 貸出状況の変更. */
	public boolean changeStatus() {
		String preStatus = status;
		status = STATUS.AVAILABLE.equals(status) ? STATUS.LENDERING : STATUS.AVAILABLE;
		return preStatus != status;
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
		return id + " - " + name + " : " + ("available".equals(status) ? "o" : "x");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
