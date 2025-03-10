package com.modsen.software.order.dto.request;

import java.math.BigDecimal;

public record ItemRequest(String description, int quantity, BigDecimal price) {
}
