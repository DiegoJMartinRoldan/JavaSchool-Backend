package es.javaschool.springbootosisfinal_task.services.ordersServices;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersMapper {

    @Autowired
    private ClientMapper clientMapper;



    public OrdersDTO convertEntityToDto(Orders orders){
        OrdersDTO ordersDTO = new OrdersDTO();

        ordersDTO.setId(orders.getId());
        ordersDTO.setPaymentMethod(orders.getPaymentMethod());
        ordersDTO.setDeliveryMethod(orders.getDeliveryMethod());
        ordersDTO.setPaymentStatus(orders.getPaymentStatus());
        ordersDTO.setOrderStatus(orders.getOrderStatus());
        ordersDTO.setClient(orders.getClient());
        ordersDTO.setClientsAddress(orders.getClientsAddress());



        return ordersDTO;

    }

    public Orders convertDtoToEntity(OrdersDTO ordersDTO){
        Orders orders = new Orders();

        orders.setId(ordersDTO.getId());

        orders.setPaymentMethod(ordersDTO.getPaymentMethod());
        orders.setDeliveryMethod(ordersDTO.getDeliveryMethod());
        orders.setPaymentStatus(ordersDTO.getPaymentStatus());
        orders.setOrderStatus(ordersDTO.getOrderStatus());
        orders.setClient(ordersDTO.getClient());
        orders.setClientsAddress(ordersDTO.getClientsAddress());




        return orders;

    }
}
