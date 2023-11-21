package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.repositories.ClientsAddressRepository;
import es.javaschool.springbootosisfinal_task.repositories.OrdersRepository;
import es.javaschool.springbootosisfinal_task.services.ordersServices.OrdersService;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("${order.Controller.url}")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientsAddressRepository clientsAddressRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<OrdersDTO>> listAll() {

        // Creamos un string llamamos a "autentication" que almacena el contexto de seguridad actual del usuario que intenta acceder a este endpoint y llama con .getAuthentication, la autenticación del mismo
        //Con get authorities extraemos las autoridades, incluimois un iterador para recorrer las autoridades que tenga, en nuestra app solo es una, next para obtener el siguiente objeto cuando va reccoriendo y getauthoriti devuelve la autoridad.

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String logged = authentication.getAuthorities().iterator().next().getAuthority();

        List<OrdersDTO> ordersDTOS;

        switch (logged){

            case "ROLE_ADMIN":
                ordersDTOS = ordersService.listAll(); // <-- First list method
                break;

            case "ROLE_USER":
                String loggedUsername = authentication.getName();
                Optional<Client> client = clientRepository.findByName(loggedUsername);

                if (!client.isPresent()){
                    throw new ResourceNotFoundException("ROLE_USER");
                }

                ordersDTOS = ordersService.listOrdersByName(loggedUsername); // <-- Second list method
                break;
            default:
                throw new ResourceNotFoundException("list");
                //revisar esta excepcion para personalizarla bien
        }

        if (ordersDTOS.isEmpty()) {
             throw new ResourceNotFoundException("list");
        }

        return new ResponseEntity<>(ordersDTOS, HttpStatus.OK);
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
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

    @PostMapping("/reorder/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> reorder (@PathVariable Long id){

        Optional<Orders> existingOrder = ordersRepository.findById(id);

        if (existingOrder == null){
            throw  new ResourceNotFoundException("reorder");
        }
        Orders newOrder = new Orders();
        newOrder.setPaymentMethod(existingOrder.get().getPaymentMethod());
        newOrder.setOrderStatus(existingOrder.get().getOrderStatus());
        newOrder.setOrderDate(existingOrder.get().getOrderDate());
        newOrder.setDeliveryMethod(existingOrder.get().getDeliveryMethod());
        newOrder.setPaymentStatus(existingOrder.get().getPaymentStatus());
        newOrder.setClient(existingOrder.get().getClient());
        newOrder.setClientsAddress(existingOrder.get().getClientsAddress());

        ordersRepository.save(newOrder);
        return  new ResponseEntity<>("Reorder created succesfully", HttpStatus.CREATED);

    }




    @GetMapping("/getby/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @Valid @RequestBody OrdersDTO ordersDTO) {
        try {
            ordersService.updateClient(ordersDTO);
            return new ResponseEntity<>("Order updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("update", "id", id);
        }
    }

                            //Update Order Status
    @PutMapping("/update/status/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public  ResponseEntity<String> updateOrderStatus (@PathVariable Long id, @RequestBody OrdersDTO ordersDTO){
        try{
            ordersService.updateOrderStatus(id, ordersDTO.getOrderStatus());
            return  new ResponseEntity<>("Order status updated", HttpStatus.OK);

        }catch (EntityNotFoundException exception){
            throw  new ResourceNotFoundException("update", "id", id);

        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
