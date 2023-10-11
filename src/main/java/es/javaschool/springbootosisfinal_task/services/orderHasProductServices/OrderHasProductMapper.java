package es.javaschool.springbootosisfinal_task.services.orderHasProductServices;

import es.javaschool.springbootosisfinal_task.domain.Order;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import es.javaschool.springbootosisfinal_task.dto.OrderHasProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHasProductMapper {

    public OrderHasProductDTO convertEntityToDto (OrderHasProduct orderHasProduct){
        OrderHasProductDTO orderHasProductDTO = new OrderHasProductDTO();
        orderHasProductDTO.setId(orderHasProductDTO.getId());
        orderHasProductDTO.setQuantity(orderHasProduct.getQuantity());

        return  orderHasProductDTO;

    }

    public OrderHasProduct convertDtoToEntity (OrderHasProductDTO orderHasProductDTO){

        OrderHasProduct orderHasProduct = new OrderHasProduct();
        orderHasProduct.setId(orderHasProductDTO.getId());
        orderHasProduct.setQuantity(orderHasProduct.getQuantity());

        return orderHasProduct;
    }
}
