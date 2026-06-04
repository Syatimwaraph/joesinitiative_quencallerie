package dev.syatimwaraph.quencallerie_mngt_v1.repository;

import dev.syatimwaraph.quencallerie_mngt_v1.model.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {
}