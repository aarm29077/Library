package com.example.Library.dto.bookStock;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

public class BookStockDTOResponse {
    private @Getter @Setter Long id;

    @Min(value = 0, message = "Minimum current quantity should be 0")
    private @Getter @Setter int currentQuantity;

    @Min(value = 1, message = "Minimum total quantity should be 1")
    private @Getter @Setter int totalQuantity;

    @Override
    public String toString() {
        return '|' + "bookStockId=" + id +
                ", currentQuantity=" + currentQuantity +
                ", totalQuantity=" + totalQuantity +
                '|';
    }
}
