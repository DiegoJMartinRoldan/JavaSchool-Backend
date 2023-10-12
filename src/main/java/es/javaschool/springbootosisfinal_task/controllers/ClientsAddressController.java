package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.dto.ClientsAddresDTO;
import es.javaschool.springbootosisfinal_task.services.clientAddresServices.ClientAddressService;
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


    //List of Clients Addresses
    @GetMapping("/list")
    public String listAll(Model model) {
        List<ClientsAddresDTO> clientsAddresDTOS = clientAddressService.listAll();
        model.addAttribute("clientsAddress", clientsAddresDTOS);
        return "clientAddress/list";


    }


    @GetMapping("/create")
    public String createPage(Model model) {

        ClientsAddresDTO clientsAddresDTO = new ClientsAddresDTO();
        model.addAttribute("clientsAddress", clientsAddresDTO);
        return "clientAddress/create";
    }

    @PostMapping("/create")
    public String createClientAddress(ClientsAddresDTO clientsAddresDTO) {
        clientAddressService.createClientAddress(clientsAddresDTO);
        return "redirect:/clientAddress/list";
    }

    @GetMapping("/getby/{id}")
    public String getClientAddressById(@PathVariable Long id, Model model) {
        ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
        model.addAttribute("clientsAddress", clientsAddress);
        return "/clientAddress/getbyid";
    }

    @GetMapping("/update{id}")
    public String updatePage(@PathVariable Long id, Model model) {
        ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
        model.addAttribute("clientsAddress", clientsAddress);
        return "clientsAddress/update";
    }

      @PostMapping("/update")
      public String updateClientAddress(@ModelAttribute("clientsAddress") ClientsAddresDTO clientsAddresDTO) {
          clientAddressService.updateClientAddress(clientsAddresDTO);
          return "redirect:/clientsAddress/list";

      }

    @DeleteMapping("/delete{id}")
    public RedirectView delete(@PathVariable Long id) {
        clientAddressService.delete(id);
        return new RedirectView("/clientsAddress/list", true);


    }
}
