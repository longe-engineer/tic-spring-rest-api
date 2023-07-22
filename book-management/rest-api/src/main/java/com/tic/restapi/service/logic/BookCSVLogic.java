package com.tic.restapi.service.logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tic.restapi.dto.Book;

@Service
public class BookCSVLogic {

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
    public boolean addBook(Book book) throws Exception {

        List<Book> books = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
                .withType(Book.class).build().parse();
        book.setId((long) (books.size() + 1));
        books.add(book);

        writeBeanToCsv(books);
        
        return true;
    }

    /**
     * 本のステータスを変更する.<br>
     * 
     * @param targetIds
     * @throws Exception
     */
    public boolean changeBookStatus(long... targetIds) throws Exception {
        boolean isChanged = true;
        List<Book> books;
        try {
            books = new CsvToBeanBuilder<Book>(new CSVReader(new FileReader(new File(CSV_PATH))))
                    .withType(Book.class)
                    .build()
                    .parse()
                    .stream()
                    .map(b ->
                    // mapメソッドの「->の後ろ」では、とりあえずなんでも良いからreturnする
                    {
                        for (long targetId : targetIds) {
                            if (b.getId() == targetId){
                                b.changeStatus();
                                break;
                            }
                        }
                        return b;
                    })
                    // リターンした値のListを作成する（今回の場合はBook）
                    .toList();

            writeBeanToCsv(books);
        } catch (Exception e) {
            e.printStackTrace();
            isChanged = false;
        }
        return isChanged;
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
}
