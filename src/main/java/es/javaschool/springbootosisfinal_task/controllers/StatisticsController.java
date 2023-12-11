package es.javaschool.springbootosisfinal_task.controllers;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.dto.RequestDto;
import es.javaschool.springbootosisfinal_task.services.ShoppingCartService;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import es.javaschool.springbootosisfinal_task.services.ordersServices.OrdersService;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ShoppingCartService.class);
    @Autowired
    private final OrdersService ordersService;

    @Autowired
    private final ProductService productService;


    @Autowired
    private final ClientService clientService;


    public StatisticsController(OrdersService ordersService, ProductService productService, ClientService clientService) {
        this.ordersService = ordersService;
        this.productService = productService;
        this.clientService = clientService;
    }


    //TOP 10 PRODUCT
    @GetMapping("/topProducts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Product>> getTop10ProductsSold() {
        List<Product> top10Products = productService.getTop10ProductsSold();
        return new ResponseEntity<>(top10Products, HttpStatus.OK);
    }

    // TOP CLIENTS
    @GetMapping("/topClients")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Client>> getTopClients (){
        List<Client> top10Clients = clientService.getTopClients();
        return new ResponseEntity<>(top10Clients,HttpStatus.OK);
    }

    // MONTHLY-WEEKLY REVENUE

      @PostMapping("/monthly")
      @PreAuthorize("hasAuthority('ROLE_ADMIN')")
      public Double getMonthlyEarnings(@RequestBody RequestDto requestDto) {

        int year = requestDto.getYear();
        int month = requestDto.getMonth();
          return ordersService.calculateMonthlyRevenue(year,month);
      }

    @GetMapping("/weekly")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Double> getWeeklyEarnings() {
        try {
            Double weeklyEarnings = ordersService.calculateWeeklyRevenue();
            log.info("Weekly Earnings: " + weeklyEarnings);
            return ResponseEntity.ok(weeklyEarnings);
        } catch (Exception e) {
            log.error("Error calculating weekly earnings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
