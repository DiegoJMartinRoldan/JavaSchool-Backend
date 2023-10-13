package es.javaschool.springbootosisfinal_task.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_has_product")
public class OrderHasProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "quantity")
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
