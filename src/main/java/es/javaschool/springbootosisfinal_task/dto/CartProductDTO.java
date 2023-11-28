package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDTO {

    private Long clientId;
    private List<ProductDTO> products;
    private List<Integer> quantities;
    private OrdersDTO ordersDTO;
    private Long clientAddressId;
}