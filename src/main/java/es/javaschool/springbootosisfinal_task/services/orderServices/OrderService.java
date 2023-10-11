package es.javaschool.springbootosisfinal_task.services.orderServices;

import es.javaschool.springbootosisfinal_task.domain.Order;
import es.javaschool.springbootosisfinal_task.dto.OrderDTO;
import es.javaschool.springbootosisfinal_task.repositories.OrderRepository;
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
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public List<OrderDTO> listAll() {
        return  orderRepository
                .findAll()
                .stream()
                .map(orderMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public void createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.convertDtoToEntity(orderDTO);
        orderRepository.save(order);
    }


    public Order getOrderById(Long id) {
        return orderRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    public void updateClient(OrderDTO orderDTO) {
        Order existing = getOrderById(orderDTO.getId());
        Order converted = orderMapper.convertDtoToEntity(orderDTO);

        existing.setPaymentMethod(converted.getPaymentMethod());
        existing.setDeliveryMethod(converted.getDeliveryMethod());
        existing.setPaymentStatus(converted.getPaymentStatus());
        existing.setOrderStatus(converted.getOrderStatus());

        orderRepository.save(existing);



    }
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }


}
