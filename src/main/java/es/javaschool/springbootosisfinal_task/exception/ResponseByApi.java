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


//We do not include the date in this constructor because it is taking it automatically
    //We also put: this.url = url.replace("uri=", ""); Because we want to make us replace what we get as url, which is ur: uri: /client/noseque, to just url: that is, an empty space as shown.

    public ResponseByApi(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}


