package es.javaschool.springbootosisfinal_task.services.ordersServices;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.repositories.OrdersRepository;
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
public class OrdersService {


    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;



    public List<OrdersDTO> listAll() {  // ADMIN Method
        return  ordersRepository
                .findAll()
                .stream()
                .map(ordersMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<OrdersDTO> listOrdersByName(String username) {  // USER Method
        List<Orders> orders = ordersRepository.findByClientName(username); // Asegúrate de que findByClientUsername acepta un String como argumento y devuelve una lista de Orders
        return orders.stream()
                .map(ordersMapper::convertEntityToDto) // Asegúrate de que convertEntityToDto acepta un objeto Orders como argumento
                .collect(Collectors.toList());
    }

    //-----------------------


    public void createOrder(OrdersDTO ordersDTO) {
        Orders order = ordersMapper.convertDtoToEntity(ordersDTO);
        ordersRepository.save(order);
    }


    public Orders getOrderById(Long id) {
        return ordersRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    public void updateClient(OrdersDTO ordersDTO) {
        Orders existing = getOrderById(ordersDTO.getId());
        Orders converted = ordersMapper.convertDtoToEntity(ordersDTO);

        existing.setPaymentMethod(converted.getPaymentMethod());
        existing.setDeliveryMethod(converted.getDeliveryMethod());
        existing.setPaymentStatus(converted.getPaymentStatus());
        existing.setOrderStatus(converted.getOrderStatus());

        ordersRepository.save(existing);

    }
    public void delete(Long id) {
        ordersRepository.deleteById(id);
    }



}
