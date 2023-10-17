package es.javaschool.springbootosisfinal_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//404 ERROR
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {


    private String resourceName;

    private String tableFieldName;

    private Object TableFieldValue;



//Para una lista no necesitamos pasar el nombre de la entidad que estamos buscando solamente necesitamos el nombre del recurso, y en este caso el mensaje que imprimimos es que la lista no se puede encontrar.

    public ResourceNotFoundException(String resourceName) {
        super(String.format("This %s does not exist in the system", resourceName));
        this.resourceName = resourceName;

    }

    //Con super le decimos que nos extienda de la herencia el mensaje que se va a imprimir, en este caso extiende de RuntimeException
   // Con String.format ponemos el mensaje que se va a mostrar a continuación. Con %s formateamos ese valor por resource name, tablefieldname y tablefield value

    public ResourceNotFoundException(String resourceName, String tableFieldName, Object tableFieldValue) {
        super(String.format("%s with %s= '%s' Not Found", resourceName, tableFieldName, tableFieldValue));
        this.resourceName = resourceName;
        this.tableFieldName = tableFieldName;
        TableFieldValue = tableFieldValue;
    }
}
