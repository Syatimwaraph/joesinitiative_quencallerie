package dev.syatimwaraph.quencallerie_mngt_v1.service;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.UserOrgDTO;
import dev.syatimwaraph.quencallerie_mngt_v1.model.UserOrg;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.UserOrgRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserOrgService {

    private final UserOrgRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserOrgService(UserOrgRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserOrgDTO dto) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            UserOrg user = new UserOrg();
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(dto.getRole());

            userRepository.save(user);
    }
}

