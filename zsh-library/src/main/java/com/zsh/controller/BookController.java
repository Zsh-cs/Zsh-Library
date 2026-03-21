package com.zsh.controller;

import com.zsh.entity.Book;
import com.zsh.result.Result;
import com.zsh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图书相关接口
 */
@RestController
@RequestMapping("/admin/book")
public class BookController {

    @Autowired
    private BookService bookService;

    // 新增图书
    @PostMapping
    public Result add(@RequestBody Book book){
        bookService.add(book);
        return Result.success();
    }
}
