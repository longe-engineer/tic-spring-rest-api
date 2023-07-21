package com.tic;

import java.util.List;
import java.util.stream.IntStream;

import com.tic.service.BookCSVService;
import com.tic.service.BookJSONService;

@SuppressWarnings("unused")
public class App {
    private static final BookJSONService jsonService = new BookJSONService();
    private static final BookCSVService csvService = new BookCSVService();

    public static void main(String[] args) throws Exception {

        csvService.changeBookStatus(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // // 大量データの貸出状況を一括ランダム変更
        // List<Integer> intlist = IntStream.range(0, 10000)
        //         .boxed()
        //         .map(i -> (int) (Math.random() * (double) 350))
        //         .toList();
        // long[] ints = new long[intlist.size()];
        // intlist.forEach(i -> {
        //     ints[intlist.indexOf(i)] = i;
        //     System.out.println(i);
        // });
        // csvService.changeBookStatus(ints);
    }
}
