package es.javaschool.springbootosisfinal_task.services;
import es.javaschool.springbootosisfinal_task.repositories.OrderHasProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderHasProductService {

    @Autowired
    private OrderHasProductRepository orderHasProductRepository;

}
