package com.modsen.software.order.mapper;

import com.modsen.software.order.dto.request.DeliveryRequest;
import com.modsen.software.order.dto.response.OrderResponse;
import com.modsen.software.order.kafka.producer.OrderStatusDetails;
import com.modsen.software.order.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);
    Order toOrderModel(DeliveryRequest deliveryRequest);
    OrderStatusDetails toStatusDetails(Order order);
}
