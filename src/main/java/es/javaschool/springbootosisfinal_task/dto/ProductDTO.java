package es.javaschool.springbootosisfinal_task.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {


    private Long id;

    @NotEmpty(message = "Title is required")
    private String title;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotEmpty(message = "Category is required")
    private String category;

    @NotEmpty(message = "Parameters are required")
    private String parameters;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.01", message = "Weight must be greater than 0")
    private BigDecimal weight;

    @NotNull(message = "Volume is required")
    @DecimalMin(value = "0.01", message = "Volume must be greater than 0")
    private BigDecimal volume;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity must be a positive value")
    private Integer quantityStock;


}
