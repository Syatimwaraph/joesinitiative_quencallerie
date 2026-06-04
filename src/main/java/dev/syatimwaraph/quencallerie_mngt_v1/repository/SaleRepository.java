package dev.syatimwaraph.quencallerie_mngt_v1.repository;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByReceiptNumber(String receiptNumber);
    List<Sale> findBySaleDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );


    List<Sale> findTop5ByOrderBySaleDateDesc();
}
