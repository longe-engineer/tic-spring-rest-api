package com.tic.restapi.dto.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

/**
 * LocalDateのフォーマット対応
 */
@SuppressWarnings("rawtypes")
public class DateConverter extends AbstractBeanField {

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(value, dtf);
	}

}
