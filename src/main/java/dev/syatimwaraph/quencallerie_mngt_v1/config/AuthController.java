package dev.syatimwaraph.quencallerie_mngt_v1.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logged-out")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error";
    }
}