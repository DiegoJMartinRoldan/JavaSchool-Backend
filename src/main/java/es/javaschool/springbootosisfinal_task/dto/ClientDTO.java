package es.javaschool.springbootosisfinal_task.dto;

import lombok.*;

import java.util.Date;

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
