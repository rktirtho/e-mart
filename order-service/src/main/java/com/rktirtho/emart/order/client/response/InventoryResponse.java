package com.rktirtho.emart.order.client.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponse {
    private String sku;
    private boolean isInStock;
}
