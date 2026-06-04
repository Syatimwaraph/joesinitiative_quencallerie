package dev.syatimwaraph.quencallerie_mngt_v1.repository;


import dev.syatimwaraph.quencallerie_mngt_v1.model.UserOrg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOrgRepository extends JpaRepository<UserOrg, Long> {
    Optional<UserOrg> findByUsername(String username);
    boolean existsByUsername(String username);
}