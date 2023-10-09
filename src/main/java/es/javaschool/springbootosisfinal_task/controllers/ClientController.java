package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.services.clientservice.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("${client.Controller.url}")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/list")
    private String listClients (Model model){
        model.addAttribute("client", clientService.listclient());
        return "client/client";


    }





}
