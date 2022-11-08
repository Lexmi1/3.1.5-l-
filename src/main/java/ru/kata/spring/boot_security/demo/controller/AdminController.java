package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;

    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping()
    public String getUsers(Model model, Principal principal) {
        User authorizedUser = userServiceImp.findByUsername(principal.getName());
        User user = new User();
        model.addAttribute("userList", userServiceImp.getUsers());
        model.addAttribute("user", user);
        model.addAttribute("authorizedUser", authorizedUser);
        return "admin";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {

        userServiceImp.save(user);

        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update2(@ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        userServiceImp.updateUser(id, user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/admin";
    }
}