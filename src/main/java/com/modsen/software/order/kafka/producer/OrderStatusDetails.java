package com.modsen.software.order.kafka.producer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonSerialize
public class OrderStatusDetails {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("status")
    private String status;
}
