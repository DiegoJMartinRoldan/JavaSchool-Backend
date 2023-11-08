package es.javaschool.springbootosisfinal_task.config.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {

    // Represents the refresh request

    public String token;

}
