package es.javaschool.springbootosisfinal_task.config.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenDTO {

    private String accessToken;
    private String token;
    private String role;
    private Long id;


}
