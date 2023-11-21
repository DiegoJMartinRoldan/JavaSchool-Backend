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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/list")
    public ResponseEntity<List<OrderHasProductDTO>> listAll() {
        List<OrderHasProductDTO> orderHasProductDTOS = orderHasProductService.listAll();
        if (orderHasProductDTOS.isEmpty()) {
            throw new ResourceNotFoundException("list");
        }
        return new ResponseEntity<>(orderHasProductDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> createOrderHasProduct(@Valid @RequestBody OrderHasProductDTO orderHasProductDTO) {

       Long ordersId = orderHasProductDTO.getOrders().getId();
       Long productsId = orderHasProductDTO.getProduct().getId();

       Orders orders = ordersRepository.findById(ordersId).orElse(null);
       Product product = productRepository.findById(productsId).orElse(null);

        if (orders != null && product != null) {
            orderHasProductDTO.setOrders(orders);
            orderHasProductDTO.setProduct(product);
            orderHasProductService.createOrderHasProduct(orderHasProductDTO);
            return new ResponseEntity<>("OrderHasProduct created successfully", HttpStatus.CREATED);
        } else {
            throw new ResourceNotFoundException("create");
        }
    }

    @GetMapping("/getby/{id}")
    public ResponseEntity<OrderHasProduct> getOrderHasProductById(@PathVariable Long id) {
        try {
            OrderHasProduct orderHasProduct = orderHasProductService.getOrderHasProductById(id);
            if (orderHasProduct == null) {
                throw new ResourceNotFoundException("getby", "id", id);
            }
            return new ResponseEntity<>(orderHasProduct, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("getby", "id", id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrderHasProduct(@PathVariable Long id, @Valid @RequestBody OrderHasProductDTO orderHasProductDTO) {
        try {
            orderHasProductService.updateOrderHasProduct(orderHasProductDTO);
            return new ResponseEntity<>("OrderHasProduct updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("update", "id", id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            OrderHasProduct orderHasProduct = orderHasProductService.getOrderHasProductById(id);
            if (orderHasProduct == null) {
                throw new ResourceNotFoundException("delete", "id", id);
            }
            orderHasProductService.delete(id);
            return new ResponseEntity<>("OrderHasProduct deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("delete", "id", id);
        }
    }
}
