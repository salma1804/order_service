package com.fooddelivery.order_service.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long menuItemId;
    private int quantity;
    private BigDecimal price;
}
