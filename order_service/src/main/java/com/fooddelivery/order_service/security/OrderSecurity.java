package com.fooddelivery.order_service.security;

import com.fooddelivery.order_service.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class OrderSecurity {

    private final OrderService orderService;

    public OrderSecurity(OrderService orderService) {
        this.orderService = orderService;
    }

    public boolean isOrderOwner(Long orderId, Long userId) {
        return orderService.getOrderById(orderId).getCustomerId().equals(userId);
    }

    public boolean isAssignedToDelivery(Long orderId, Long deliveryId) {
        Long assignedDeliveryId = orderService.getOrderById(orderId).getDeliveryId();
        return assignedDeliveryId != null && assignedDeliveryId.equals(deliveryId);
    }

}
