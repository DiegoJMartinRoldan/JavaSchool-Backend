package es.javaschool.springbootosisfinal_task.services.orderHasProductServices;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import es.javaschool.springbootosisfinal_task.dto.OrderHasProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHasProductMapper {

    public OrderHasProductDTO convertEntityToDto (OrderHasProduct orderHasProduct){

        OrderHasProductDTO orderHasProductDTO = new OrderHasProductDTO();

        orderHasProductDTO.setId(orderHasProduct.getId());
        orderHasProductDTO.setQuantity(orderHasProduct.getQuantity());
        orderHasProductDTO.setOrders(orderHasProduct.getOrders());
        orderHasProduct.setProduct(orderHasProduct.getProduct());

        return  orderHasProductDTO;

    }

    public OrderHasProduct convertDtoToEntity (OrderHasProductDTO orderHasProductDTO){

        OrderHasProduct orderHasProduct = new OrderHasProduct();

        orderHasProduct.setId(orderHasProductDTO.getId());
        orderHasProduct.setQuantity(orderHasProductDTO.getQuantity());
        orderHasProduct.setOrders(orderHasProductDTO.getOrders());
        orderHasProduct.setProduct(orderHasProductDTO.getProduct());

        return orderHasProduct;
    }
}
