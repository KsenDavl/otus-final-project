package ru.otus.spring.davlks.meetingservice.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.davlks.meetingservice.security.entity.Role;
import ru.otus.spring.davlks.meetingservice.security.entity.User;
import ru.otus.spring.davlks.meetingservice.security.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getStartPage() {
        return "login";
    }

    @GetMapping("/success")
    public String authenticatedPage() {
        return "redirect:/start";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        user.setRoles(List.of(new Role(1, "USER"))); //todo enum
        userService.saveUser(user);
        return "redirect:/";
    }
}
