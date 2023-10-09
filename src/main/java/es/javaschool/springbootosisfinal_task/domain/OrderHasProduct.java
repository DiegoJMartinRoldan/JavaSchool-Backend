package es.javaschool.springbootosisfinal_task.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class OrderHasProduct {

    @Id
    private Long id;


   // @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

  //  @Id
    @ManyToOne
    @JoinColumn(name = "productr_id")
    private Product product;

      private int quantity;
}
