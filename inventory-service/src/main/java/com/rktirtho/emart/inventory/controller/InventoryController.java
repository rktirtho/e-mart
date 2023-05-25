package com.rktirtho.emart.inventory.controller;

import com.rktirtho.emart.inventory.dto.InventoryResponse;
import com.rktirtho.emart.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/inventory")
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("isInStock")
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes) {
        log.info("Calling inventory Service");
        return inventoryService.isInStockBySku(skuCodes);
    }
    @GetMapping
    public Map<String, String> hello(@RequestHeader Map<String, String> headers) {
        log.info("Calling inventory Service");
        var g=  new HashMap<String, String>();
        g.put("Message", "Hello from inventory service");
        g.putAll(headers);
        return g;
    }
    @GetMapping("test")
    public boolean isInStock() {
        log.info("Calling inventory Service");
        return true;
    }
}
