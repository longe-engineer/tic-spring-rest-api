package com.tic.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tic.restapi.dto.History;
import com.tic.restapi.service.RequestService;

/**
 * 申請Contoller.<br>
 */
@CrossOrigin
@RestController
@RequestMapping("request")
public class RequestController {

    private final RequestService service;

    public RequestController(RequestService service) {
        this.service = service;
    }

    @PostMapping("buy")
    public ResponseEntity<History> buy(@RequestBody String bookName) throws Exception {
        if (!service.buy(bookName)) {
            System.out.println("An error occured with what we don't understand in detail.");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("borrow/{id}")
    public ResponseEntity<History> borrow(@PathVariable("id") long id) throws Exception {
        if (!service.borrow(id)) {
            System.out.println(id + " is bad id (e.x. not to find book)");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("return/{id}")
    public ResponseEntity<History> returnOf(@PathVariable("id") long id) throws Exception {
        if (!service.returnOf(id)) {
            System.out.println(id + " is bad id (e.x. not to find book)");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
