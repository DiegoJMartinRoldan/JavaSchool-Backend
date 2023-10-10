package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("${client.Controller.url}")
public class ClientController {


    @Autowired
    private ClientService clientService;


//List Clients
 @GetMapping("/list")
 public String listAll(Model model){
     List<ClientDTO> clientDTOS = clientService.listAll();
     model.addAttribute("clients", clientDTOS);
     return "client/list";
 }


//Create Clients
 @GetMapping("/create")
 public String createPage(Model model){
     ClientDTO clientDTO = new ClientDTO();
     model.addAttribute("clientCreate", clientDTO);
     return "client/create";
 }

 @PostMapping("/create")
    public String createClient (ClientDTO clientDTO){
     clientService.createClient(clientDTO);
     return "redirect:/client/list";

 }


 //Get Clients by Id
 @GetMapping("/getby/{id}")
 public String getClientById(@PathVariable Long id, Model model){
     Client client = clientService.getClientById(id);
     model.addAttribute("clients", client);
     return "/client/getbyid";

 }


 //Update Clients
  @GetMapping("/update/{id}")
  public String updatePage(@PathVariable Long id, Model model){
     Client client = clientService.getClientById(id);
     model.addAttribute("clientUpdate", client);
     return "client/update";

    }
 @PostMapping("/update")
 public String Update(@ModelAttribute("clientUpdate") ClientDTO clientDTO) {
     clientService.updateClient(clientDTO);
     return "redirect:/client/list";
    }

    //Delete Clients
    @PostMapping("/delete/{id}")
    public String Delete(@PathVariable Long id){
     clientService.delete(id);
     return "redirect:/client/list";
    }









 //Update, Delete, Getbyid





}
