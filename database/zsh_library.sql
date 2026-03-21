create database zsh_library;
use zsh_library;

create table book (
    id bigint primary key auto_increment,
    title varchar(255) not null comment '书名',
    author varchar(100) not null comment '作者',
    isbn varchar(20) unique not null comment 'ISBN编号',
    description varchar(255) comment '简介',
    publisher varchar(100) not null comment '出版社',
    publish_date date not null comment '出版日期',
    price double not null comment '定价'
) comment '图书表';