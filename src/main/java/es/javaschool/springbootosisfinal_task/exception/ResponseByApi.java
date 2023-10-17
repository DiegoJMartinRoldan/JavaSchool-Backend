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


    //No incluimos la fecha en este constructor porque lo está cogiendo de forma automática
    //Tambien ponemos: this.url = url.replace("uri=", ""); Porque queremos uqe nos haga replace de lo que nos sale como url, que es ur: uri: /client/noseque, a solo url: es decir, un espacio vacío como se muestra.

    public ResponseByApi(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}


