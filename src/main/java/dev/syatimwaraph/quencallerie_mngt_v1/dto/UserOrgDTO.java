package dev.syatimwaraph.quencallerie_mngt_v1.dto;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.Roles;
import jakarta.validation.constraints.NotBlank;

public class UserOrgDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Roles role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
