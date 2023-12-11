package es.javaschool.springbootosisfinal_task.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "clients_address")
@JsonIgnoreProperties({"orders"})
public class ClientsAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String street;

    @Column(name = "home")
    private String home;

    @Column(name = "apartment")
    private String apartment;

    @OneToMany(mappedBy = "clientsAddress", cascade = CascadeType.ALL)
    private List<Orders> orders;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_id")
    private Client client;







}
