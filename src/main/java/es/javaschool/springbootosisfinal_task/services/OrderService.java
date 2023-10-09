package es.javaschool.springbootosisfinal_task.services;
import es.javaschool.springbootosisfinal_task.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

}
