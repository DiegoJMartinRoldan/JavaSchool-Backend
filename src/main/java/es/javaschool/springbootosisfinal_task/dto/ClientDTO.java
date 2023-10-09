package es.javaschool.springbootosisfinal_task.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ClientDTO {


    private String name;
    private String surname;
    private Date dateOfBirth;
    private String email;
    private String password;
}
