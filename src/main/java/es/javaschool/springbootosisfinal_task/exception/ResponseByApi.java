package es.javaschool.springbootosisfinal_task.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ResponseByApi {

    private Date time = new Date();

    private String message;

    private String url;


    public ResponseByApi(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}


