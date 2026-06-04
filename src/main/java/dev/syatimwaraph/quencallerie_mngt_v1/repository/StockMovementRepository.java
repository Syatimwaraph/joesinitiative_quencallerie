package dev.syatimwaraph.quencallerie_mngt_v1.repository;

import dev.syatimwaraph.quencallerie_mngt_v1.model.StockMouvement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMouvement, Long> {
}