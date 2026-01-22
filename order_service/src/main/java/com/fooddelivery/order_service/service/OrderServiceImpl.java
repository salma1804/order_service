package com.fooddelivery.order_service.service;

import com.fooddelivery.order_service.dto.OrderDTO;
import com.fooddelivery.order_service.dto.OrderItemDTO;
import com.fooddelivery.order_service.model.Order;
import com.fooddelivery.order_service.model.OrderItem;
import com.fooddelivery.order_service.model.OrderStatus;
import com.fooddelivery.order_service.repository.OrderRepository;
import com.fooddelivery.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                .restaurantId(orderDTO.getRestaurantId())
                .customerId(orderDTO.getCustomerId())
                .status(OrderStatus.PLACED)
                .createdAt(LocalDateTime.now())
                .orderItems(orderDTO.getOrderItems().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList()))
                .totalPrice(calculateTotal(orderDTO.getOrderItems()))
                .build();

        order = orderRepository.save(order);
        return mapToDTO(order);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<OrderDTO> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        order = orderRepository.save(order);
        return mapToDTO(order);
    }

    private BigDecimal calculateTotal(List<OrderItemDTO> items) {
        return items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderDTO mapToDTO(Order order) {
        List<OrderItemDTO> items = order.getOrderItems().stream()
                .map(i -> new OrderItemDTO(i.getMenuItemId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .restaurantId(order.getRestaurantId())
                .customerId(order.getCustomerId())
                .orderItems(items)
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }

    private OrderItem mapToEntity(OrderItemDTO dto) {
        return OrderItem.builder()
                .menuItemId(dto.getMenuItemId())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
    }
}
