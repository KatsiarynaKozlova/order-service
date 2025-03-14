package com.modsen.software.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    private Long id;
    private Long orderId;
    private String description;
    private int quantity;
    private BigDecimal price;
}
