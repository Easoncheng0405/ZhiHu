package com.jlu.chengjie.zhihu.model;

/*
 *@Author chengjie
 *@Date 2018-12-21
 *@Email chengjie.jlu@qq.com
 */

public class Question {

    private int id;

    private User author;

    private String title;

    private String content;

    private long st;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSt() {
        return st;
    }

    public void setSt(long st) {
        this.st = st;
    }
}
