create database zsh_library;
use zsh_library;

create table book (
    id bigint primary key auto_increment,
    title varchar(255) not null comment '书名',
    author varchar(100) not null comment '作者',
    isbn varchar(20) unique comment 'ISBN编号',
    description varchar(255) comment '简介',
    publisher varchar(100) comment '出版社',
    publish_date date comment '出版日期',
    price decimal(10, 2) comment '定价'
) comment '图书表';