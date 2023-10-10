package es.javaschool.springbootosisfinal_task.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

//@Data
//@Data includes: @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Using .identity because the id can be autoincremental and there are no specific uses
    private Long id;

  //  @Column(name ="name" )
    private String name;
   // @Column(name ="name" )
    private String surname;
    private Date dateOfBirth;
    private String email;
    private String password;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientsAddress> clientsAddresses;




}
