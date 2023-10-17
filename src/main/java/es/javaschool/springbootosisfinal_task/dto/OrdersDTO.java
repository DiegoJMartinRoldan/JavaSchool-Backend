package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private Long id;


    @NotEmpty(message = "Payment method is required")
    private String paymentMethod;

    @NotEmpty(message = "Delivery method is required")
    private String deliveryMethod;

    @NotEmpty(message = "Payment status is required")
    private String paymentStatus;

    @NotEmpty(message = "Order status is required")
    private String orderStatus;

    @NotNull(message = "Client is required")
    private Client client;

    @NotNull(message = "Client's address is required")
    private ClientsAddress clientsAddress;
}
