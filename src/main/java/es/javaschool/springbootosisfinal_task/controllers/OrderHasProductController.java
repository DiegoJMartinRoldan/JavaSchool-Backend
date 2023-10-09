package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.services.OrderHasProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${orderHasProducct.Controller.url}")
public class OrderHasProductController {

    @Autowired
    private OrderHasProductService orderHasProductService;

}
