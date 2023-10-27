package es.javaschool.springbootosisfinal_task.dto;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

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

    @NotNull
    private Date orderDate;


    private Client client;

    private ClientsAddress clientsAddress;
}
