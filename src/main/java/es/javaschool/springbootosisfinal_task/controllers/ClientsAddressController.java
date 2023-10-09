package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.services.ClientAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${clientsAdress.Controller.url}")
public class ClientsAddressController {

    @Autowired
    private ClientAddressService clientAddressService;


}
