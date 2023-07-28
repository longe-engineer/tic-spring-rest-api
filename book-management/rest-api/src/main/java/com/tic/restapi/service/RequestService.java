package com.tic.restapi.service;

import java.util.List;

import com.tic.restapi.dto.History;

/**
 * 申請サービス.<br>
 * 
 * @author Ciu
 * @version 1.0.0
 */
public interface RequestService {

	/** 
	 * 本の購入申請.<br>
	 * 
	 * @param bookName
	 * @return
	 * @throws Exception
	 */
    boolean buy(String bookName) throws Exception;

    /**
     * 本の貸出申請.<br>
     * 
     * @param id
     * @return
     * @throws Exception 本のステータスが貸出中の場合発生
     */
    boolean borrowBook(long id) throws Exception;

    /**
     * 本の返却申請.<br>
     * @param id
     * @return
     * @throws Exception 本のステータスが利用可能の場合発生
     */
    boolean returnBook(long id) throws Exception;
    
    /**
     * 申請履歴の確認.<br>
     * @param rowNum
     * @return
     * @throws Exception
     */
    List<History> getHistory(long rowNum) throws Exception;
}
