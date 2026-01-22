package com.fooddelivery.order_service.dto;

import com.fooddelivery.order_service.model.OrderStatus;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private Long restaurantId;
    private Long customerId;
    private List<com.fooddelivery.order_service.dto.OrderItemDTO> orderItems;
    private BigDecimal totalPrice;
    private OrderStatus status;
}
