package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/users")
    public String users() {
        return "users";
    }

}