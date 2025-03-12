package com.modsen.software.order.dto.response;

import java.math.BigDecimal;

public record ItemResponse(Long id, Long orderId, String description, int quantity, BigDecimal price) {
}
