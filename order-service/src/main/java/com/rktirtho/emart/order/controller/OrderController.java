package com.rktirtho.emart.order.controller;

import com.rktirtho.emart.order.dto.OrderRequest;
import com.rktirtho.emart.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public void placeOrder (@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
    }

}
