package com.example.Library.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BookCustomerId implements Serializable {

    private Long bookId;
    private Long customerId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
