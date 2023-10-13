package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private Long id;
    private String paymentMethod;
    private String deliveryMethod;
    private String paymentStatus;
    private String orderStatus;
    private Client client;
    private ClientsAddress clientsAddress;
}
