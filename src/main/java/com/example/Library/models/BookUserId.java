package com.example.Library.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BookUserId implements Serializable {

    private Long book_id;
    private Long user_id;

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
