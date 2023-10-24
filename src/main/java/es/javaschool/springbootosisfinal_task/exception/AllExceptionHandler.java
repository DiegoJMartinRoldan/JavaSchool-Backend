package es.javaschool.springbootosisfinal_task.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

//Customizing Exceptions
@RestControllerAdvice
public class AllExceptionHandler {

    //First 2 handmade exceptions, without ProblemDetail, we replace ProblemDetails with the ResponseByApi class

    //@Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){

        Map<String, String> errormap = new HashMap<>();
       exception.getBindingResult().getAllErrors().forEach((error)->{
                String key = ((FieldError)error).getField();
                String value = error.getDefaultMessage();
                errormap.put(key,value);
               }
       );
        ResponseByApi responseByApi = new ResponseByApi(errormap.toString(), webRequest.getDescription(false));
        return new ResponseEntity<>(responseByApi, HttpStatus.BAD_REQUEST);
    }





    // Exception is thrown when a specific requested resource is not found on the server, Converts the default text that I have created in ResourceNotFoundException into a json and not into a text as it was configured
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseByApi> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){

        ResponseByApi responseByApi = new ResponseByApi(exception.getMessage(), webRequest.getDescription(false));


        return new ResponseEntity<>(responseByApi, HttpStatus.NOT_FOUND);
    }







    //Spring Security Exceptions. Here we use ProblemDetails instead ResponseByApi class to manage exceptions
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception){

        ProblemDetail problemDetail = null;
        //Bad Credentials 401 (Authentication error)
        if (exception instanceof BadCredentialsException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            problemDetail.setProperty("access_denied_reason","Authentication Failure");

        }
        //Access Denied 403 (Authorization error)
        if (exception instanceof AccessDeniedException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            problemDetail.setProperty("access_denied_reason","Authorization Failure, not authorized");

        }

        //Invalid Jwt 403 (SignatureException)
        if (exception instanceof SignatureException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            problemDetail.setProperty("access_denied_reason","Jwt Signature not valid");
        }

        //Token Expired 403
        if (exception instanceof ExpiredJwtException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            problemDetail.setProperty("access_denied_reason","Token expired");
        }


        return problemDetail;
    }




}
