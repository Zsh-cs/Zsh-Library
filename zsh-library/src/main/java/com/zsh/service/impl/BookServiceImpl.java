package com.zsh.service.impl;

import com.zsh.entity.Book;
import com.zsh.mapper.BookMapper;
import com.zsh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    // 新增图书
    @Override
    public void add(Book book) {
        bookMapper.insert(book);
    }
}
