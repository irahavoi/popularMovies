package com.example.irahavoi.popularmovies.domain;

import java.io.Serializable;

public class Review implements Serializable {
    private String id;
    private String author;
    private String content;

    public Review(){}

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
