package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.dto.ClientsAddresDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.services.clientAddresServices.ClientAddressService;
import jakarta.persistence.EntityNotFoundException;
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
        model.addAttribute("clientsAddress", clientsAddresDTOS);
        return "clientsAddress/list";


    }

    @GetMapping("/create/{clientId}")
    public String createPage(@PathVariable Long clientId, Model model) {
        // Verifica si el cliente con el ID proporcionado existe
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));

        ClientsAddresDTO clientsAddresDTO = new ClientsAddresDTO();
        clientsAddresDTO.setClient(client); // Asigna el cliente al DTO
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientsAddress", clientsAddresDTO);

        return "clientsAddress/create";
    }

    @PostMapping("/create/{clientId}")
    public String createClientAddress(@PathVariable Long clientId, ClientsAddresDTO clientsAddresDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        clientsAddresDTO.setClient(client);

        clientAddressService.createClientAddress(clientsAddresDTO);
        return "redirect:/clientsAddress/list";
    }






    // @GetMapping("/create")
  // public String createPage(Model model) {

  //     ClientsAddresDTO clientsAddresDTO = new ClientsAddresDTO();
  //     model.addAttribute("clientsAddress", clientsAddresDTO);
  //     return "clientsAddress/create";
  // }

  // @PostMapping("/create")
  // public String createClientAddress(ClientsAddresDTO clientsAddresDTO) {
  //     Long clientId = clientsAddresDTO.getClient().getId();
  //     Client client = clientRepository
  //             .findById(clientId)
  //             .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
  //     clientsAddresDTO.setClient(client);
  //     clientAddressService.createClientAddress(clientsAddresDTO);
  //     return "redirect:/clientsAddress/list";
  // }

    @GetMapping("/getby/{id}")
    public String getClientAddressById(@PathVariable Long id, Model model) {
        ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
        model.addAttribute("clientsAddress", clientsAddress);
        return "/clientsAddress/getbyid";
    }

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable Long id, Model model) {
        ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
        model.addAttribute("clientsAddressUpdate", clientsAddress);
        return "clientsAddress/update";
    }

      @PostMapping("/update")
      public String updateClientAddress(@ModelAttribute("clientsAddressUpdate") ClientsAddresDTO clientsAddresDTO) {
          clientAddressService.updateClientAddress(clientsAddresDTO);
          return "redirect:/clientsAddress/list";

      }

    @DeleteMapping("/delete{id}")
    public RedirectView delete(@PathVariable Long id) {
        clientAddressService.delete(id);
        return new RedirectView("/clientsAddress/list", true);


    }
}
