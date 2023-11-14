package com.example.Library.dto;

import jakarta.validation.constraints.Min;

public class BookStockDTOResponse {
    private Long id;

    @Min(value = 0, message = "Minimum current quantity should be 0")
    private int currentQuantity;

    @Min(value = 1, message = "Minimum total quantity should be 1")
    private int totalQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return '|' + "bookStockId=" + id +
                ", currentQuantity=" + currentQuantity +
                ", totalQuantity=" + totalQuantity +
                '|';
    }
}
