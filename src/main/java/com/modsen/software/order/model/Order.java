package com.modsen.software.order.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private Long id;
    private Long userId;
    private Long driverId;
    private String pickupLocation;
    private String deliveryLocation;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal totalPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
