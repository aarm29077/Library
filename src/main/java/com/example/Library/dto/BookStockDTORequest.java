package com.example.Library.dto;

import jakarta.validation.constraints.Min;


public class BookStockDTORequest {

    private Long bookId;

    @Min(value = 1, message = "The minimum quantity is 1")
    private int quantity;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
