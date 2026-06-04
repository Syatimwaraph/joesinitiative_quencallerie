package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.model.User;
import dev.syatimwaraph.quencallerie_mngt_v1.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/populate")
    public String populate() {
        userService.enrichDb();
        return "OK";
    }


    @GetMapping("/getUsers")
    public Page<User> getUsers(@RequestParam Integer page, @RequestParam Integer size,
                               @RequestParam String sortingField, @RequestParam String sortingDirection) {
        log.info("handling users request: {}, {}, {}, {}", page, size, sortingField, sortingDirection);
        return userService.fetchUsers(page, size, sortingField, sortingDirection);
    }
}
