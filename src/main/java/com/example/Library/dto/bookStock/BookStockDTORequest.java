package com.example.Library.dto.bookStock;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


public class BookStockDTORequest {

    private @Getter @Setter Long bookId;

    @Min(value = 1, message = "The minimum quantity is 1")
    private @Getter @Setter int quantity;

}
