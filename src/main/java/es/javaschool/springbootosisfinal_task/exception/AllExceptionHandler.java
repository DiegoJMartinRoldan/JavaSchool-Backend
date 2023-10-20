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

//This annotation means that this class will detect all the errors that will occur within the controller, with the correct methods we can customize the message it throws
@RestControllerAdvice
public class AllExceptionHandler {


    //For when the @Valid fails in any of the fields that have it, handle the exception
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

        //LLamamos a ResponseApi y creamos una instancia, le mandamos el mensaje, y la ruta mediante webrequest para que me salga en falso para que solo me de los detalles de donde proviene el error
        ResponseByApi responseByApi = new ResponseByApi(exception.getMessage(), webRequest.getDescription(false));


        return new ResponseEntity<>(responseByApi, HttpStatus.NOT_FOUND);
    }






    //Error due to incorrect client request.
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ResponseByApi> handlerBadRequestException(BadRequestException exception, WebRequest webRequest){

      ResponseByApi responseByApi = new ResponseByApi(exception.getMessage(), webRequest.getDescription(false));


      return new ResponseEntity<>(responseByApi, HttpStatus.BAD_REQUEST);
  }





}
