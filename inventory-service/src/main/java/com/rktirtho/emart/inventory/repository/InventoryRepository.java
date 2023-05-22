package com.rktirtho.emart.inventory.repository;

import com.rktirtho.emart.inventory.dto.InventoryResponse;
import com.rktirtho.emart.inventory.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findBySkuCodeIn(List<String> skuCodes);
}
