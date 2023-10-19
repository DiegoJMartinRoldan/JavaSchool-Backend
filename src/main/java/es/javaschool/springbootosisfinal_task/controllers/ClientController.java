package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("${client.Controller.url}")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/list")
    public ResponseEntity<List<ClientDTO>> listAll() {
        List<ClientDTO> clientDTOS = clientService.listAll();
        if (clientDTOS.isEmpty()) {
            throw new ResourceNotFoundException("list");
        }
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClient(@Valid @RequestBody ClientDTO clientDTO) {

        try {
            clientService.createClient(clientDTO);
            return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
        }catch (Exception e){
            throw  new ResourceNotFoundException("create");
        }


    }

    @GetMapping("/getby/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        try{
            Client client = clientService.getClientById(id);

            if (client == null) {
                throw new ResourceNotFoundException("getby", "id", id);
            }
            return new ResponseEntity<>(client, HttpStatus.OK);

        }catch (EntityNotFoundException exception){
            throw  new ResourceNotFoundException("getby", "id", id);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {

        try{
            clientService.updateClient(clientDTO);
            return new ResponseEntity<>("Client updated successfully", HttpStatus.OK);
        }catch (EntityNotFoundException exception){
            throw  new ResourceNotFoundException("update", "id", id);
        }


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        try {
            Client client = clientService.getClientById(id);

            if (client == null) {
                throw new ResourceNotFoundException("delete", "id", id);
            }

            clientService.delete(id);
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);

        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("delete", "id", id);
        }

    }
}

