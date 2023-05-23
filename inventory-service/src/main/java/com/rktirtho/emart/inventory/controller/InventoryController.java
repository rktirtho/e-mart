package com.rktirtho.emart.inventory.controller;

import com.rktirtho.emart.inventory.dto.InventoryResponse;
import com.rktirtho.emart.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("test")
    public boolean isInStock() {
        log.info("Calling inventory Service");
        return true;
    }
}
