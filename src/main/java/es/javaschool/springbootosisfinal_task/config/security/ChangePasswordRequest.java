package es.javaschool.springbootosisfinal_task.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    //Que es lo que te va a pedir el método de cambiar contraseña para poder ejecutarse
    private String email;

    private String oldPwd;

    private String newPwd;
}
