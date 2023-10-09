package es.javaschool.springbootosisfinal_task.domain;

import jakarta.persistence.*;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private DecimalFormat price;
    private String category;
    private String parameters;
    private DecimalFormat weight;
    private DecimalFormat volume;
    private int quantityStock;

    @OneToMany (mappedBy = "product")
    private List<OrderHasProduct> orderHasProducts;


}
