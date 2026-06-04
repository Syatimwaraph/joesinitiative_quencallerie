package dev.syatimwaraph.quencallerie_mngt_v1.repository;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
