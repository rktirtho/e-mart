package com.rktirtho.emart.product.service;

import com.rktirtho.emart.product.dto.ProductRequest;
import com.rktirtho.emart.product.model.Product;
import com.rktirtho.emart.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(ProductRequest request){
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        product = productRepository.save(product);
        log.info("Created product id id {}", product.getId());
        return product;
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
}
