package es.javaschool.springbootosisfinal_task.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_method")
    private String paymentMethod;


    @Column(name = "delivery_method")
    private String deliveryMethod;


    @Column(name = "payment_status")
    private String paymentStatus;


    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @ManyToOne
    @JoinColumn(name = "clients_address_id")
    private ClientsAddress clientsAddress;


    @OneToMany(mappedBy = "orders")
    private List<OrderHasProduct> orderHasProducts;



}
