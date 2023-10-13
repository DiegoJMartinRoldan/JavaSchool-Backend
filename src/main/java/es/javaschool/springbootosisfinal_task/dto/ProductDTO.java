package es.javaschool.springbootosisfinal_task.dto;

import lombok.*;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {


    private Long id;
    private String title;
    private BigDecimal price;
    private String category;
    private String parameters;
    private BigDecimal weight;
    private BigDecimal volume;
    private Integer quantityStock;

}
