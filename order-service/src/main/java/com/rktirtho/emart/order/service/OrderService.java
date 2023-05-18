package com.rktirtho.emart.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
public class OrderService {
    private final WebClient webClient;

    void placeOrder() {
        List<String> values = List.of("");
        webClient.get()
                .uri("",
                        urlBuilder ->  urlBuilder
                                    .queryParam("skuCode", values)
                                    .build()
                        )
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();


    }
}
