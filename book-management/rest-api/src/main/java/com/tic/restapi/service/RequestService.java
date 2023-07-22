package com.tic.restapi.service;

/**
 * 申請サービス.<br>
 * 
 * @author Ciu
 * @version 1.0.0
 */
public interface RequestService {

    boolean buy(String bookName) throws Exception;

    boolean borrow(long id) throws Exception;

    boolean returnOf(long id) throws Exception;
}
