package com.zsh.controller;

import com.zsh.entity.Book;
import com.zsh.result.Result;
import com.zsh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 根据id删除单本图书
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id){
        bookService.deleteById(id);
        return Result.success();
    }

    // 根据id编辑单本图书
    @PutMapping
    public Result updateById(@RequestBody Book book){
        bookService.updateById(book);
        return Result.success();
    }

    // 根据id查询单本图书
    @GetMapping("/{id}")
    public Result<Book> getById(@PathVariable Long id){
        Book book=bookService.getById(id);
        return Result.success(book);
    }

    // 展示所有图书
    @GetMapping("/list")
    public Result<List<Book>> list(){
        List<Book> books=bookService.list();
        return Result.success(books);
    }

    // 根据条件查询图书
    @GetMapping
    public Result<List<Book>> getByCondition(Integer condition,String name){
        List<Book> books=bookService.getByCondition(condition,name);
        return Result.success(books);
    }
}
