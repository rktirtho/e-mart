package com.rktirtho.emart.order.controller;

import com.rktirtho.emart.order.dto.OrderRequest;
import com.rktirtho.emart.order.entity.OrderEntity;
import com.rktirtho.emart.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping("/place")
//    @CircuitBreaker(name = "order", fallbackMethod = "orderFallBack")
//    @TimeLimiter(name = "order", fallbackMethod = "orderFallBackDelay")
//    @Retry(name = "order")
//    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
//        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest) );
//    }

    @PostMapping("/place")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping
    public List<OrderEntity> getAll(){
        return orderService.findAll();
    }


    public CompletableFuture<String> orderFallBack(OrderRequest request, RuntimeException e) {
        return CompletableFuture.supplyAsync(() -> "Order failed. Try again later");

    }

    public CompletableFuture<String> orderFallBackDelay(OrderRequest request, RuntimeException e) {
        return CompletableFuture.supplyAsync(() -> "Time limit exit...");

    }
}