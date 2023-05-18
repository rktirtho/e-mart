package com.rktirtho.emart.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document( value = "products")
public class Product {
    private String id;
    private String sku;
    private String name;
    private String brand;
    private String price;
}
