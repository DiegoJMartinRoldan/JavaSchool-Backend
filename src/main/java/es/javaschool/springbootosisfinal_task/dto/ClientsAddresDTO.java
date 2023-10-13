package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Client;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientsAddresDTO {

    private Long id;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String home;
    private String apartment;
    private Client client;




}
