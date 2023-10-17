package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.dto.ClientsAddresDTO;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.services.clientAddresServices.ClientAddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("${clientsAdress.Controller.url}")
public class ClientsAddressController {

    @Autowired
    private ClientAddressService clientAddressService;

    @Autowired
    private ClientRepository clientRepository;


    //List of Clients Addresses
    @GetMapping("/list")
    public String listAll(Model model) {
        List<ClientsAddresDTO> clientsAddresDTOS = clientAddressService.listAll();

        if (clientsAddresDTOS == null || clientsAddresDTOS.isEmpty()){
            throw new ResourceNotFoundException("list");
        }


        model.addAttribute("clientsAddress", clientsAddresDTOS);
        return "clientsAddress/list";


    }



    @GetMapping("/create")
     public String createPage(Model model) {

         ClientsAddresDTO clientsAddresDTO = new ClientsAddresDTO();
         model.addAttribute("clientsAddress", clientsAddresDTO);
         return "clientsAddress/create";
     }

     @PostMapping("/create")
     public String createClientAddress(@Valid @ModelAttribute("clientsAddress") ClientsAddresDTO clientsAddresDTO, @RequestParam("client.id") Long clientId) {

         Client client = clientRepository.findById(clientId).orElse(null);

         if (client != null){
             clientsAddresDTO.setClient(client);
             clientAddressService.createClientAddress(clientsAddresDTO);
             return "redirect:/clientsAddress/list";
         }else {
             throw new ResourceNotFoundException("create");
         }

     }



    @GetMapping("/getby/{id}")
    public String getClientAddressById(@PathVariable Long id, Model model) {

        try{
            ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
            model.addAttribute("clientsAddress", clientsAddress);
            return "/clientsAddress/getbyid";
        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException("getby","id", id);
        }


    }

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable Long id, Model model) {

       try{
           ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
           model.addAttribute("clientsAddressUpdate", clientsAddress);
           return "clientsAddress/update";
       }catch (EntityNotFoundException exception){
           throw new ResourceNotFoundException("update", "id", id);
       }


    }

      @PostMapping("/update")
      public String updateClientAddress(@Valid @ModelAttribute("clientsAddressUpdate") ClientsAddresDTO clientsAddresDTO) {
          clientAddressService.updateClientAddress(clientsAddresDTO);
          return "redirect:/clientsAddress/list";

      }

    @DeleteMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id) {
        clientAddressService.delete(id);
        return new RedirectView("/clientsAddress/list", true);


    }
}
