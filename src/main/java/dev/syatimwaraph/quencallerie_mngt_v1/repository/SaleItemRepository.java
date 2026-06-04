package dev.syatimwaraph.quencallerie_mngt_v1.repository;

import dev.syatimwaraph.quencallerie_mngt_v1.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository
        extends JpaRepository<SaleItem, Long> {
}