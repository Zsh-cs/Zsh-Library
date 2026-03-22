package com.zsh.service;

import com.zsh.entity.Book;

import java.util.List;

public interface BookService {

    // 新增图书
    void add(Book book);

    // 根据id删除单本图书
    void deleteById(Long id);

    // 根据id编辑单本图书
    void updateById(Book book);

    // 根据id查询单本图书
    Book getById(Long id);

    // 展示所有图书
    List<Book> list();

    // 根据条件查询图书
    List<Book> getByCondition(Integer condition, String name);
}
