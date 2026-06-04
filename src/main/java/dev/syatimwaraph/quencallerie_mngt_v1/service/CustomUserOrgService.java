package dev.syatimwaraph.quencallerie_mngt_v1.service;

import dev.syatimwaraph.quencallerie_mngt_v1.model.UserOrg;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.UserOrgRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserOrgService implements UserDetailsService {

    private final UserOrgRepository  userOrgRepository;

    public CustomUserOrgService(UserOrgRepository userOrgRepository) {
        this.userOrgRepository = userOrgRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserOrg user = userOrgRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
