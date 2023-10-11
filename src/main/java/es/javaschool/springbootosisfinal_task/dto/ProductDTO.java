package es.javaschool.springbootosisfinal_task.dto;

import lombok.*;

import java.text.DecimalFormat;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {


    private Long id;
    private String title;
    private DecimalFormat price;
    private String category;
    private String parameters;
    private DecimalFormat weight;
    private DecimalFormat volume;
    private int quantityStock;
}
