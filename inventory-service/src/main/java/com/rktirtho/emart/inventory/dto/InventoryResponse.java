package com.rktirtho.emart.inventory.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {
    private String sku;
    private boolean isInStock;
}
