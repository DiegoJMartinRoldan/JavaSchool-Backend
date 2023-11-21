package es.javaschool.springbootosisfinal_task.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    //Hasta ahora no habiamos hecho cambios en esta clase, pero con la llegada de las excpeciones podemos incluir validaciones en los dto para que sea posible acceder a esta información al crearse por ejemplo

    private Long id;

    @Size(min = 2, max = 20)
    @NotNull(message = "Name cannot be null")
    private String name;

    @Size(min = 2, max = 20, message = "Surname must be between 2 and 20 characters")
    @NotEmpty (message = "Surname is required")
    private String surname;


    private Date dateOfBirth;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String password;

    private String role;


    public ClientDTO(Long id, String name, Long totalOrders) {

    }
}
