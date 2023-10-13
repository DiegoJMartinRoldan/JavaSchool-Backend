package es.javaschool.springbootosisfinal_task.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String email;

}
