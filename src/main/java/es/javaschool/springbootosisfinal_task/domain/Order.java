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
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paymentMethod")
    private String paymentMethod;


    @Column(name = "deliveryMethod")
    private String deliveryMethod;


    @Column(name = "paymentStatus")
    private String paymentStatus;


    @Column(name = "orderStatus")
    private String orderStatus;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @ManyToOne
    @JoinColumn(name = "clients_address_id")
    private ClientsAddress clientsAddress;


    @OneToMany(mappedBy = "order")
    private List<OrderHasProduct> orderHasProducts;



}
