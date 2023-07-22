package com.tic.restapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.tic.restapi.dto.converter.DateConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 申請のEntity.
 * 
 * @author Ciu
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "date",
    "book_id",
    "type"
})
public class History {

    /** 申請日付. */
    @JsonProperty("date")
    @CsvCustomBindByName(column = "date", converter = DateConverter.class)
    private LocalDate date;

    /**
     * 本のID.
     */
    @JsonProperty("book_id")
    @CsvBindByName(column = "book_id")
    private long bookId;

    /** 申請タイプ. */
    @JsonProperty("type")
    @CsvBindByName(column = "type")
    private String type;

    public static final class RequestType {
        public static final String BUY = "buy";
        public static final String BORROW = "borrow";
        public static final String RETURN = "return";
    }
}
