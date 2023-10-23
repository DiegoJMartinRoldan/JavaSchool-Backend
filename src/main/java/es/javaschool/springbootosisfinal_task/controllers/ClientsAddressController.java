package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.dto.ClientsAddresDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.services.clientAddresServices.ClientAddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientsAddress")
public class ClientsAddressController {

    @Autowired
    private ClientAddressService clientAddressService;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ClientsAddresDTO>> listAll() {
        List<ClientsAddresDTO> clientsAddresDTOS = clientAddressService.listAll();
        if (clientsAddresDTOS.isEmpty()) {
            throw new ResourceNotFoundException("list");
        }
        return new ResponseEntity<>(clientsAddresDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClientAddress(@Valid @RequestBody ClientsAddresDTO clientsAddresDTO) {
        Client client = clientRepository.findById(clientsAddresDTO.getClient().getId()).orElse(null);
        if (client != null) {
            clientsAddresDTO.setClient(client);
            clientAddressService.createClientAddress(clientsAddresDTO);
            return new ResponseEntity<>("Client address created successfully", HttpStatus.CREATED);
        } else {
            throw new ResourceNotFoundException("create");
        }
    }

    @GetMapping("/getby/{id}")
    public ResponseEntity<ClientsAddress> getClientAddressById(@PathVariable Long id) {
        try {
            ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
            if (clientsAddress == null) {
                throw new ResourceNotFoundException("getby", "id", id);
            }
            return new ResponseEntity<>(clientsAddress, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("getby", "id", id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClientAddress(@PathVariable Long id, @Valid @RequestBody ClientsAddresDTO clientsAddresDTO) {
        try {
            clientAddressService.updateClientAddress(clientsAddresDTO);
            return new ResponseEntity<>("Client address updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("update", "id", id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            ClientsAddress clientsAddress = clientAddressService.getClientAddressById(id);
            if (clientsAddress == null) {
                throw new ResourceNotFoundException("delete", "id", id);
            }
            clientAddressService.delete(id);
            return new ResponseEntity<>("Client address deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("delete", "id", id);
        }
    }
}

