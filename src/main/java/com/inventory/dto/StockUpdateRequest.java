package com.inventory.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockUpdateRequest {
    @NotNull
    private Integer delta; // can be negative
}
