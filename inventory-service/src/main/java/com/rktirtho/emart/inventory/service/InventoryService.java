package com.rktirtho.emart.inventory.service;


import com.rktirtho.emart.inventory.dto.InventoryResponse;
import com.rktirtho.emart.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public void save(){

    }

    public List<InventoryResponse> isInStockBySku(List<String> skuCodes){
        List<InventoryResponse> inventoryResponses = inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .peek(inventoryEntity -> log.info(inventoryEntity.toString()))
                .map(inventoryEntity -> InventoryResponse.builder().
                        isInStock(inventoryEntity.getQuantity() > 0).
                        sku(inventoryEntity.getSkuCode())
                        .build())
                .toList();
        return inventoryResponses;
    }
}
