package es.javaschool.springbootosisfinal_task.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_has_product")
@JsonIgnoreProperties({"orders"})
public class OrderHasProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "quantity")
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
