package es.javaschool.springbootosisfinal_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//404 ERROR
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {


    private String resourceName;

    private String tableFieldName;

    private Object TableFieldValue;



//For a list we do not need to pass the name of the entity we are looking for, we only need the name of the resource, and in this case the message we print is that the list cannot be found.

    public ResourceNotFoundException(String resourceName) {
        super(String.format("This %s does not exist in the system", resourceName));
        this.resourceName = resourceName;

    }

//With super we tell it to extend the message to be printed from the inheritance, in this case it extends RuntimeException
    // With String.format we put the message that will be shown next. With %s we format that value by resource name, tablefieldname and tablefield value

    public ResourceNotFoundException(String resourceName, String tableFieldName, Object tableFieldValue) {
        super(String.format("%s with %s= '%s' Not Found", resourceName, tableFieldName, tableFieldValue));
        this.resourceName = resourceName;
        this.tableFieldName = tableFieldName;
        TableFieldValue = tableFieldValue;
    }
}
