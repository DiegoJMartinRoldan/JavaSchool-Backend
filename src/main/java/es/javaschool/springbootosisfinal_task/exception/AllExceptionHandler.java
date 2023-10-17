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

//Esta anotación significa que esta clase va a detectar todos los errores que se vayan a producir dentro del controlador, con los metodos correctos podremos personalizar el mensae que arroja
@RestControllerAdvice
public class AllExceptionHandler {


    //Para cuando falla el @Valid en alguno de los campos que lo tienen, maneja la excepcion
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





    // La excepción se lanza cuando un recurso específico solicitado no se encuentra en el servidor
    // Me convierte el texto por defecto que yo tengo creado en ResourceNotFoundException en un json y no en un texto como estaba configurado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseByApi> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){

        //LLamamos a ResponseApi y creamos una instancia, le mandamos el mensaje, y la ruta mediante webrequest para que me salga en falso para que solo me de los detalles de donde proviene el error
        ResponseByApi responseByApi = new ResponseByApi(exception.getMessage(), webRequest.getDescription(false));


        return new ResponseEntity<>(responseByApi, HttpStatus.NOT_FOUND);
    }






  //Error por una solicitud incorrecta del cliente.
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ResponseByApi> handlerBadRequestException(BadRequestException exception, WebRequest webRequest){

      ResponseByApi responseByApi = new ResponseByApi(exception.getMessage(), webRequest.getDescription(false));


      return new ResponseEntity<>(responseByApi, HttpStatus.BAD_REQUEST);
  }





}
