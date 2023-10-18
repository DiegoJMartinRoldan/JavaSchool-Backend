package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.exception.BadRequestException;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@Controller
@RequestMapping("${client.Controller.url}")
public class ClientController {


    @Autowired
    private ClientService clientService;


    //List Clients
    @GetMapping("/list")
    public String listAll(Model model){
        List<ClientDTO> clientDTOS = clientService.listAll();

        if (clientDTOS == null || clientDTOS.isEmpty()){
            throw  new ResourceNotFoundException("list");
        }

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
    public String createClient (@Valid ClientDTO clientDTO){
        clientService.createClient(clientDTO);
        return "redirect:/client/list";

    }


    //Get Clients by Id
    @GetMapping("/getby/{id}")
    public String getClientById( @PathVariable Long id, Model model){

        try{
            Client client = clientService.getClientById(id);
            model.addAttribute("clients", client);
            return "/client/getbyid";

        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException("getby","id", id);
        }

    }


    //Update Clients
    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable Long id, Model model){

        try {
            Client client = clientService.getClientById(id);
            model.addAttribute("clients", client);
            return "client/update";

        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException("update", "id", id);
        }


    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("clients") ClientDTO clientDTO) {
        clientService.updateClient(clientDTO);
        return "redirect:/client/list";
    }



    //Delete Clients
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        clientService.delete(id);
        return "redirect:/client/list";

    }


}
