package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${product.Controller.url}")
public class ProductController {


    @Autowired
    private ProductService productService;

}
