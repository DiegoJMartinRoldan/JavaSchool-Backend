package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Client;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientsAddresDTO {

    private Long id;

    @NotEmpty(message = "Country required")
    private String country;


    @NotEmpty (message = "City required")
    private String city;


    @NotEmpty (message = "Postal Code required")
    private String postalCode;


    @NotEmpty (message = "Street required")
    private String street;


    @NotEmpty (message = "Home required")
    private String home;


    @NotEmpty (message = "Apartment required")
    private String apartment;


    @NotNull(message = "Client required")
    private Client client;




}
