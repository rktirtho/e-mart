package com.rktirtho.emart.order.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @ToString.Exclude
    private OrderEntity order;
}
