package com.rktirtho.emart.order.service;

import com.rktirtho.emart.order.client.response.InventoryResponse;
import com.rktirtho.emart.order.dto.OrderLineItemsDto;
import com.rktirtho.emart.order.dto.OrderRequest;
import com.rktirtho.emart.order.entity.OrderEntity;
import com.rktirtho.emart.order.entity.OrderItemsEntity;
import com.rktirtho.emart.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {
    private final WebClient.Builder webClientBuilder;
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(UUID.randomUUID().toString());

        orderEntity.setItems(
                orderRequest.getOrderLineItemsDtoList()
                        .stream()
                        .map(this::mapToDto).toList()
        );


        List<String> orderSkuCode = orderRequest.getOrderLineItemsDtoList().stream()
                        .map(OrderLineItemsDto::getSkuCode).toList();

        boolean bol = webClientBuilder.build().get()
                .uri("http://INVENTORY-SERVICE/api/va/inventory/test",
                        urlBuilder -> urlBuilder
                                .queryParam("skuCodes", orderSkuCode)
                                .build()
                )
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        InventoryResponse[] result = webClientBuilder.build().get()
                .uri("http://INVENTORY-SERVICE/api/va/inventory/isInStock",
                        urlBuilder -> urlBuilder
                                .queryParam("skuCodes", orderSkuCode)
                                .build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean isInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);
        if (isInStock){
            orderRepository.save(orderEntity);
        }else log.info("Product is not in stock");

    }

    private OrderItemsEntity mapToDto(OrderLineItemsDto order) {
        OrderItemsEntity entity = new OrderItemsEntity();
        entity.setPrice(order.getPrice());
        entity.setQuantity(order.getQuantity());
        entity.setSkuCode(order.getSkuCode());
        return entity;
    }
}
