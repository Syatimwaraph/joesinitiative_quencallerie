package dev.syatimwaraph.quencallerie_mngt_v1.dto;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Category is required")
    private ProductCategory productCategory;

    @Min(value = 0, message = "Purchase price cannot be negative")
    private double purchasePrice;

    @Min(value = 1, message = "Selling price must be greater than 0")
    private double sellingPrice;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    private String unit;

    // getters & setters
}