package es.javaschool.springbootosisfinal_task.services.orderHasProductServices;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.OrderHasProductDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.repositories.OrderHasProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHasProductService {

    @Autowired
    private OrderHasProductRepository orderHasProductRepository;

    @Autowired
    private OrderHasProductMapper orderHasProductMapper;

    public List<OrderHasProductDTO> listAll() {
        return orderHasProductRepository.findAll()
                .stream()
                .map(orderHasProductMapper::convertEntityToDto)
                .collect(Collectors.toList());

    }

    public void createOrderHasProduct(OrderHasProductDTO orderHasProductDTO) {

        OrderHasProduct orderHasProduct = orderHasProductMapper.convertDtoToEntity(orderHasProductDTO);
        orderHasProductRepository.save(orderHasProduct);



    }

    public OrderHasProduct getOrderHasProductById(Long id) {
        return orderHasProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));

    }

    public void updateOrderHasProduct(OrderHasProductDTO orderHasProductDTO) {
        OrderHasProduct existing = getOrderHasProductById(orderHasProductDTO.getId());
        OrderHasProduct converted = orderHasProductMapper.convertDtoToEntity(orderHasProductDTO);

        existing.setQuantity(converted.getQuantity());

        orderHasProductRepository.save(existing);


    }

    public void delete(Long id) {
        orderHasProductRepository.deleteById(id);
    }



}
