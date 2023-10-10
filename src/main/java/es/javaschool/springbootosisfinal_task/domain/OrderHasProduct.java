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
    private Long id;


   // @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

  //  @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

      private int quantity;
}
