package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.domain.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHasProductDTO {


    private Long id;

    @NotNull(message = "Quantity required")
    @Min(value = 1, message = "Quantity must be a positive value")
    private Integer quantity;

    @NotNull(message = "Orders required")
    private Orders orders;

    @NotNull(message = "Product required")
    private Product product;
}
