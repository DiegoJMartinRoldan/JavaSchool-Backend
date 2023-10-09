package es.javaschool.springbootosisfinal_task.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;
    private String deliveryMethod;
    private String paymentStatus;
    private String orderStatus;

     @ManyToOne
     @JoinColumn(name = "client_id")
     private Client client;

     @ManyToOne
     @JoinColumn(name = "clientsAddress_id")
     private ClientsAddress clientsAddress;

     @OneToMany(mappedBy = "order")
     private List<OrderHasProduct> orderHasProducts;



}
