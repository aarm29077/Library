package com.example.Library.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BookCustomerId implements Serializable {

    private Long book_id;
    private Long customer_id;

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }
}
