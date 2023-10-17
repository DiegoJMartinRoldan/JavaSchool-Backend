package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.OrderHasProductDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.repositories.OrderHasProductRepository;
import es.javaschool.springbootosisfinal_task.repositories.OrdersRepository;
import es.javaschool.springbootosisfinal_task.repositories.ProductRepository;
import es.javaschool.springbootosisfinal_task.services.orderHasProductServices.OrderHasProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Order;
import jakarta.validation.Valid;
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

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping ("/list")
    public String listAll(Model model){
        List<OrderHasProductDTO> orderHasProductDTOS = orderHasProductService.listAll();

        if (orderHasProductDTOS == null || orderHasProductDTOS.isEmpty()){
            throw  new ResourceNotFoundException("list");
        }

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
    public String createOrderHasProduct (@Valid @ModelAttribute("orderHasProductsCreate") OrderHasProductDTO orderHasProductDTO, @RequestParam("orders.id") Long clientId, @RequestParam("product.id") Long productId){

        Orders orders = ordersRepository.findById(clientId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (orders !=  null && product != null){
            orderHasProductDTO.setOrders(orders);
            orderHasProductDTO.setProduct(product);
            orderHasProductService.createOrderHasProduct(orderHasProductDTO);
            return "redirect:/orderHasProduct/list";
        }else {
            throw new ResourceNotFoundException("create");
        }

    }

    @GetMapping("/getby/{id}")
    public String getOrderHasProductById (@PathVariable Long id, Model model){

        try {
            OrderHasProduct orderHasProduct = orderHasProductService.getOrderHasProductById(id);
            model.addAttribute("orderHasProducts", orderHasProduct);
            return "orderHasProduct/getbyid";
        }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException("getby","id", id);
        }


    }

    @GetMapping ("/update/{id}")
    public String updatePage (@PathVariable Long id, Model model){

        try{
           OrderHasProduct orderHasProduct = orderHasProductService.getOrderHasProductById(id);
           model.addAttribute("orderHasProducts", orderHasProduct);
           return "orderHasProduct/update";
       }catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException("update", "id", id);
        }




    }

    @PostMapping ("/update")
    public String updateOrderHasProduct (@Valid @ModelAttribute("orderHasProducts") OrderHasProductDTO orderHasProductDTO){
        orderHasProductService.updateOrderHasProduct(orderHasProductDTO);
        return "redirect:/orderHasProduct/list";
    }


    @DeleteMapping ("/delete/{id}")

    public RedirectView delete (@PathVariable Long id){
        orderHasProductService.delete(id);
        return new RedirectView("/orderHasProduct/list", true);

    }


}
