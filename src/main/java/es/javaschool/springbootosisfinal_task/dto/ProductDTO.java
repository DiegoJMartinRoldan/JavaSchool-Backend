package es.javaschool.springbootosisfinal_task.dto;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class ProductDTO {

    private String title;
    private DecimalFormat price;
    private String category;
    private String parameters;
    private DecimalFormat weight;
    private DecimalFormat volume;
    private int quantityStock;
}
