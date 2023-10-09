package es.javaschool.springbootosisfinal_task.services;
import es.javaschool.springbootosisfinal_task.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService{


@Autowired
private ProductRepository productRepository;
}
