package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import es.javaschool.springbootosisfinal_task.dto.OrderHasProductDTO;
import es.javaschool.springbootosisfinal_task.services.orderHasProductServices.OrderHasProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("${orderHasProducct.Controller.url}")
public class OrderHasProductController {

    @Autowired
    private OrderHasProductService orderHasProductService;

    @GetMapping ("/list")
    public String listAll(Model model){
        List<OrderHasProductDTO> orderHasProductDTOS = orderHasProductService.listAll();
        model.addAttribute("orderHasProducts", orderHasProductDTOS);
        return "orderHasProduct/list";
    }


    @GetMapping ("/create")
    public String createPage (Model model){
        OrderHasProductDTO orderHasProductDTO = new OrderHasProductDTO();
        model.addAttribute("orderHasProductsCreate", orderHasProductDTO);
        return "/orderHasProduct/create";

    }

    @PostMapping("/create")
    public String createOrderHasProduct (OrderHasProductDTO orderHasProductDTO){
        orderHasProductService.createOrderHasProduct(orderHasProductDTO);
        return "redirect:/orderHasProduct/list";

    }

    @GetMapping("/getby/{id}")
    public String getOrderHasProductById (@PathVariable Long id, Model model){
        OrderHasProduct orderHasProduct = orderHasProductService.getOrderHasProductById(id);
        model.addAttribute("orderHasProducts", orderHasProduct);
        return "/orderHasProduct/getbyid";
    }

    @GetMapping ("/update{id}")
    public String updatePage (@PathVariable Long id, Model model){
        OrderHasProduct orderHasProduct = orderHasProductService.getOrderHasProductById(id);
        model.addAttribute("orderHasProducts", orderHasProduct);
        return "/orderHasProduct/update";


    }

    @PostMapping ("/update")
    public String updateOrderHasProduct (@ModelAttribute("orderHasProducts") OrderHasProductDTO orderHasProductDTO){
        orderHasProductService.updateOrderHasProduct(orderHasProductDTO);
        return "redirect:/orderHasProduct/list";
    }


    @DeleteMapping ("/delete{id}")

    public RedirectView delete (@PathVariable Long id){
        orderHasProductService.delete(id);
        return new RedirectView("/orderHasProduct/list", true);

    }


}
