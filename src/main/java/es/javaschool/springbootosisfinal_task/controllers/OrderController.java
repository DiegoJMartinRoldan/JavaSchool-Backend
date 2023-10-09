package es.javaschool.springbootosisfinal_task.controllers;

import es.javaschool.springbootosisfinal_task.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${order.Controller.url}")
public class OrderController {

    @Autowired
    private OrderService orderService;

}
