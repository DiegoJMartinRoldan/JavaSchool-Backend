package es.javaschool.springbootosisfinal_task.services.orderServices;
import es.javaschool.springbootosisfinal_task.domain.Order;
import es.javaschool.springbootosisfinal_task.dto.OrderDTO;
import es.javaschool.springbootosisfinal_task.services.clientAddresServices.ClientAddressService;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMapper {


    @Autowired
    private final ClientService clientService;

    @Autowired
    private final ClientAddressService clientAddressService;



    public OrderDTO convertEntityToDto(Order order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        order.setPaymentMethod(order.getPaymentMethod());
        order.setDeliveryMethod(order.getDeliveryMethod());
        order.setPaymentStatus(order.getPaymentStatus());
        order.setOrderStatus(order.getOrderStatus());


        return orderDTO;

    }

    public Order convertDtoToEntity(OrderDTO orderDTO){
        Order order = new Order();

        order.setId(orderDTO.getId());

        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setDeliveryMethod(orderDTO.getDeliveryMethod());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        order.setOrderStatus(orderDTO.getOrderStatus());


        return order;

    }
}
