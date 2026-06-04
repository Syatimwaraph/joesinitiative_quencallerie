package dev.syatimwaraph.quencallerie_mngt_v1.repository;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // LOW STOCK
    List<Product> findByStockLessThanEqual(Integer stock);

    Page<Product> findByProductNameContainingIgnoreCase(
            String productName,
            Pageable pageable
    );
}

