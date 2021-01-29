package ru.jm.js_rest_app.project_js_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.jm.js_rest_app.project_js_rest.models.Role;
import ru.jm.js_rest_app.project_js_rest.models.User;
import ru.jm.js_rest_app.project_js_rest.service.UserService;

import java.util.List;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "service/login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) { //@ModelAttribute(name = "user") User user,
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);

        List<Role> allRoles = userService.getRoles();
        model.addAttribute("allRoles", allRoles);

        model.addAttribute("allUsers", userService.getAllUsers());

        return "administrator/adminPage";
    }

    //user controller
    @GetMapping(value = "/user/{id}")
    public String helloUser(Model model, @PathVariable("id") int id) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        User currentUser = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (currentUser.getId() != id && currentUser.getRoles().stream().noneMatch((x -> x.getName().contains("ROLE_ADMIN")))) {
            return "redirect:/user/" + currentUser.getId();
        }
        return "user/user";
    }

    //moderator controller
    @GetMapping("/moderator/{id}")
    public String helloModerator(Model model, @PathVariable("id") int id) {
        model.addAttribute("AllUsers", userService.getAllUsers());

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);

        return "moderator/ModeratorPage";
    }
}
