package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.domain.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHasProductDTO {


    private Long id;
    private Integer quantity;
    private Orders orders;
    private Product product;
}
