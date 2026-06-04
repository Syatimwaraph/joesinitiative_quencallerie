package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.UserOrgDTO;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.Roles;
import dev.syatimwaraph.quencallerie_mngt_v1.service.UserOrgService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserOrgController {

    private final UserOrgService userService;

    public UserOrgController(UserOrgService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/create")
    public String createUserPage(Model model) {
        model.addAttribute("user", new UserOrgDTO());
        model.addAttribute("roles", Roles.values());
        return "users/create-user";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") UserOrgDTO userDTO) {
        userService.save(userDTO);
        return "redirect:/users/create?success";
    }
}