package com.rktirtho.emart.inventory.service;


import com.rktirtho.emart.inventory.dto.InventoryResponse;
import com.rktirtho.emart.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public void save(){

    }

    @SneakyThrows
    public List<InventoryResponse> isInStockBySku(List<String> skuCodes){
//        Random r = new Random();
//        int low = 1000;
//        int high = 6000;
//        int result = r.nextInt(high-low) + low;
//        log.info("Waining "+result);
//        Thread.sleep(result);
        log.info("End of delay");
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .peek(inventoryEntity -> log.info(inventoryEntity.toString()))
                .map(inventoryEntity -> InventoryResponse.builder().
                        isInStock(inventoryEntity.getQuantity() > 0).
                        sku(inventoryEntity.getSkuCode())
                        .build())
                .toList();
    }
}
