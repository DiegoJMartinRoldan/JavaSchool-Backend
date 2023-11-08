package es.javaschool.springbootosisfinal_task.config.jwt;

import es.javaschool.springbootosisfinal_task.domain.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "refresh_token")
public class RefreshToken {

    // Represents the RefreshToken entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration")
    private Instant expiration;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
