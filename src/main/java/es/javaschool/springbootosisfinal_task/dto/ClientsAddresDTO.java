package es.javaschool.springbootosisfinal_task.dto;

import lombok.Data;

@Data
public class ClientsAddresDTO {

    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String home;
    private String apartment;


}
