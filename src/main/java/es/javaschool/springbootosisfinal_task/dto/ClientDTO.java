package es.javaschool.springbootosisfinal_task.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ClientDTO {

    private Long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String email;

}
