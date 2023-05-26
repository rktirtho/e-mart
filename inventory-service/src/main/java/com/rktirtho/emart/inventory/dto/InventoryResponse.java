package com.rktirtho.emart.inventory.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class InventoryResponse {
    private String sku;
    private boolean isInStock;
}
