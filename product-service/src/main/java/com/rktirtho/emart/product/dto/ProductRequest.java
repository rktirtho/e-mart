package com.rktirtho.emart.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest{
    private String sku;
    private String name;
    private String brand;
    private String price;
}
