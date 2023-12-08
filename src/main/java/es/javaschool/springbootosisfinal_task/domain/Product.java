package es.javaschool.springbootosisfinal_task.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private String category;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "volume")
    private BigDecimal volume;

    @Column(name = "image", columnDefinition = "BLOB")
    @Lob
    private byte[] image;

    @Column(name = "quantity_stock")
    private Integer quantityStock;

    @OneToMany(mappedBy = "product")
    private List<OrderHasProduct> orderHasProducts;


}