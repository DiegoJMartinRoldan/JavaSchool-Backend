package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.repositories.ClientsAddressRepository;
import es.javaschool.springbootosisfinal_task.services.ordersServices.OrdersMapper;
import es.javaschool.springbootosisfinal_task.services.ordersServices.OrdersService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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



    //List
    @GetMapping("/list")
     public  String listAll(Model model){
        List<OrdersDTO> ordersDTOS = ordersService.listAll();
        System.out.println(ordersDTOS); // Imprime los datos
        model.addAttribute("orderslist", ordersDTOS);
        return "orders/list";

    }


    //Create
    @GetMapping("/create")
    public String createPage (Model model){
        OrdersDTO ordersDTO = new OrdersDTO();
        model.addAttribute("orderCreate", ordersDTO);
        return "orders/create";

    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("orderCreate") OrdersDTO ordersDTO, @RequestParam("client.id") Long clientId, @RequestParam("address.id") Long addressId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        ClientsAddress clientsAddress = clientsAddressRepository.findById(addressId).orElse(null);
        if (client != null && clientsAddress != null) {
            ordersDTO.setClient(client);
            ordersDTO.setClientsAddress(clientsAddress);
            ordersService.createOrder(ordersDTO);
            return "redirect:/orders/list";
        } else {
            throw new RuntimeException("The client or the client's address was not found. Please check the provided values.");
        }
    }

    //Get by id

    @GetMapping("/getby/{id}")
    public  String getOrderById (@PathVariable Long id, Model model){

        Orders order = ordersService.getOrderById(id);
        model.addAttribute("orders", order);
        return "orders/getbyid";

    }

    //Update

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable Long id, Model model){
        Orders order = ordersService.getOrderById(id);
        model.addAttribute("orders", order);
        return "orders/update";
    }

    @PostMapping("/update")
    public  String updateOrder (@ModelAttribute("orders") OrdersDTO ordersDTO){
        ordersService.updateClient(ordersDTO);
        return "redirect:/orders/list";


    }

    //Delete

    @DeleteMapping ("/delete/{id}")
    public RedirectView delete (@PathVariable Long id){
        ordersService.delete(id);
        return new RedirectView("/orders/list", true);

    }






}
