package com.fooddelivery.order_service.service;

import com.fooddelivery.order_service.dto.OrderDTO;
import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long id);

    List<OrderDTO> getOrdersByCustomer(Long customerId);

    OrderDTO updateOrderStatus(Long id, String status);
}
