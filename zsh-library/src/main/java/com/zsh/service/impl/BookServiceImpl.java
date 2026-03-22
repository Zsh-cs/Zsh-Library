package com.zsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsh.entity.Book;
import com.zsh.mapper.BookMapper;
import com.zsh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    public static final Integer BY_TITLE = 1;
    public static final Integer BY_AUTHOR = 2;

    @Autowired
    private BookMapper bookMapper;

    // 新增图书
    @Override
    public void add(Book book) {
        bookMapper.insert(book);
    }

    // 根据id删除单本图书
    @Override
    public void deleteById(Long id) {
        bookMapper.deleteById(id);
    }

    // 根据id编辑单本图书
    @Override
    public void updateById(Book book) {
        bookMapper.updateById(book);
    }

    // 根据id查询单本图书
    @Override
    public Book getById(Long id) {
        return bookMapper.selectById(id);
    }

    // 展示所有图书
    @Override
    public List<Book> list() {
        return bookMapper.selectList(null);
    }

    // 根据条件查询图书
    @Override
    public List<Book> getByCondition(Integer condition, String name) {

        // 如果搜索框输入的name为null或为空，则直接展示所有图书
        if(name==null || name.isEmpty()){
            return list();
        }

        LambdaQueryWrapper<Book> lqw=new LambdaQueryWrapper<>();
        if(condition.equals(BY_TITLE)){
            lqw.like(Book::getTitle,name);
        } else if (condition.equals(BY_AUTHOR)) {
            lqw.like(Book::getAuthor,name);
        }
        return bookMapper.selectList(lqw);
    }
}
