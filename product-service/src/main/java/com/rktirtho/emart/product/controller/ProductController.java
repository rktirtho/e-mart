package com.rktirtho.emart.product.controller;

import com.rktirtho.emart.product.dto.ProductRequest;
import com.rktirtho.emart.product.model.Product;
import com.rktirtho.emart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductRequest request) {
        Product product = productService.save(request);
        return ResponseEntity.created(createLocation(product.getId())).build();

    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll () {
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    private URI createLocation(String id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
