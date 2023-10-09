package es.javaschool.springbootosisfinal_task.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private String paymentMethod;
    private String deliveryMethod;
    private String paymentStatus;
    private String orderStatus;
}
