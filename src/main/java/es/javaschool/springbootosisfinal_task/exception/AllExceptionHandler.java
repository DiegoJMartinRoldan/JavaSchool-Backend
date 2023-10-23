package es.javaschool.springbootosisfinal_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

//Customizing Exceptions
@RestControllerAdvice
public class AllExceptionHandler {


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





    // Exception is thrown when a specific requested resource is not found on the server
    // Converts the default text that I have created in ResourceNotFoundException into a json and not into a text as it was configured
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseByApi> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){

        ResponseByApi responseByApi = new ResponseByApi(exception.getMessage(), webRequest.getDescription(false));


        return new ResponseEntity<>(responseByApi, HttpStatus.NOT_FOUND);
    }





}
