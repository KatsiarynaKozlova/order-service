package com.modsen.software.order.dto.response;

import com.modsen.software.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long userId,
        Long driverId,
        String pickupLocation,
        String deliveryLocation,
        OrderStatus status,
        BigDecimal totalPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}