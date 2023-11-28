package es.javaschool.springbootosisfinal_task.services.ordersServices;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.repositories.OrdersRepository;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {


    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;


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


    public Orders createOrder(OrdersDTO ordersDTO) {
        Orders order = ordersMapper.convertDtoToEntity(ordersDTO);
        Orders savedOrder = ordersRepository.save(order);
        return savedOrder;
    }

    //---------


    public Orders getOrderById(Long id) {
        return ordersRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    //----------

    public void updateClient(OrdersDTO ordersDTO) {
        Orders existing = getOrderById(ordersDTO.getId());
        Orders converted = ordersMapper.convertDtoToEntity(ordersDTO);

        existing.setPaymentMethod(converted.getPaymentMethod());
        existing.setDeliveryMethod(converted.getDeliveryMethod());
        existing.setPaymentStatus(converted.getPaymentStatus());
        existing.setOrderStatus(converted.getOrderStatus());

        ordersRepository.save(existing);

    }

    //Method for order status changes
    public void updateOrderStatus(Long id, String orderStatus) {
        Optional<Orders> existing = ordersRepository.findById(id);
        if (existing.isPresent()){
            Orders orders = existing.get();
            orders.setOrderStatus(orderStatus);
            ordersRepository.save(orders);
        }else{
            throw new EntityNotFoundException("Order not found");
        }
    }

    //----------

    public void delete(Long id) {
        ordersRepository.deleteById(id);
    }




    //------------ Statistics
      public Double calculateMonthlyRevenue(int year, int month) {
          return ordersRepository.getMonthlyEarnings(year, month);
      }


      public Double calculateWeeklyRevenue() {
          return ordersRepository.calculateWeeklyEarnings();
      }










}
