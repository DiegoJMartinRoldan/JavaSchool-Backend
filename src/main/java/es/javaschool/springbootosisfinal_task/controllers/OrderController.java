package es.javaschool.springbootosisfinal_task.controllers;

import es.javaschool.springbootosisfinal_task.domain.Order;
import es.javaschool.springbootosisfinal_task.dto.OrderDTO;
import es.javaschool.springbootosisfinal_task.services.orderServices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("${order.Controller.url}")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //List
    @GetMapping("/list")
     public  String listAll(Model model){
        List<OrderDTO> orderDTOS = orderService.listAll();
        System.out.println(orderDTOS); // Imprime los datos
        model.addAttribute("orders", orderDTOS);
        return "order/list";

    }


    //Create
    @GetMapping("/create")
    public String createPage (Model model){
        OrderDTO orderDTO = new OrderDTO();
        model.addAttribute("orderCreate", orderDTO);
        return "/order/create";

    }

    @PostMapping("/create")
    public  String createOrder (OrderDTO orderDTO){
        orderService.createOrder(orderDTO);
        return "redirect:/order/create";
    }

    //Get by id

    @GetMapping("/getby/{id}")
    public  String getOrderById (@PathVariable Long id, Model model){

        Order order = orderService.getOrderById(id);
        model.addAttribute("orders", order);
        return "order/getbyid";

    }

    //Update

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable Long id, Model model){
        Order order = orderService.getOrderById(id);
        model.addAttribute("orders", order);
        return "order/update";
    }

    @PostMapping("/update/")
    public  String updateOrder (@ModelAttribute("orders") OrderDTO orderDTO){
        orderService.updateClient(orderDTO);
        return  "redirect:/order/list";

    }

    //Delete

    @DeleteMapping ("/delete/{id}")
    public RedirectView delete (@PathVariable Long id){
        orderService.delete(id);
        return new RedirectView("/order/list", true);

    }






}
