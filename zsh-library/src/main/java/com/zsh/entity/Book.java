package com.zsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;
    private String author;
    private String isbn;
    private String description;
    private String publisher;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    private Double price;

}
