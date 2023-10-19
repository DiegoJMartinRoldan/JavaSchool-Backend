package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.repositories.ClientsAddressRepository;
import es.javaschool.springbootosisfinal_task.services.ordersServices.OrdersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${order.Controller.url}")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientsAddressRepository clientsAddressRepository;

    @GetMapping("/list")
    public ResponseEntity<List<OrdersDTO>> listAll() {
        List<OrdersDTO> ordersDTOS = ordersService.listAll();
        if (ordersDTOS.isEmpty()) {
            throw new ResourceNotFoundException("list");
        }
        return new ResponseEntity<>(ordersDTOS, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrdersDTO ordersDTO) {

        Long clientId = ordersDTO.getClient().getId();
        Long addressId = ordersDTO.getClientsAddress().getId();

        Client client = clientRepository.findById(clientId).orElse(null);
        ClientsAddress clientsAddress = clientsAddressRepository.findById(addressId).orElse(null);

        if (client != null && clientsAddress != null) {

            ordersDTO.setClient(client);
            ordersDTO.setClientsAddress(clientsAddress);
            ordersService.createOrder(ordersDTO);
            return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);

        } else {
            throw new ResourceNotFoundException("create");
        }
    }

    @GetMapping("/getby/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        try {
            Orders order = ordersService.getOrderById(id);
            if (order == null) {
                throw new ResourceNotFoundException("getby", "id", id);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("getby", "id", id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @Valid @RequestBody OrdersDTO ordersDTO) {
        try {
            ordersService.updateClient(ordersDTO);
            return new ResponseEntity<>("Order updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("update", "id", id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            Orders order = ordersService.getOrderById(id);
            if (order == null) {
                throw new ResourceNotFoundException("delete", "id", id);
            }
            ordersService.delete(id);
            return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("delete", "id", id);
        }
    }




}
