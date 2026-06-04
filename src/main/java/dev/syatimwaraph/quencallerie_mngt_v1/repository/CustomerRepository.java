package dev.syatimwaraph.quencallerie_mngt_v1.repository;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
