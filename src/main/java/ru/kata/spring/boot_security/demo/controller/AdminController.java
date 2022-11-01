package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;

    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping()
    public String adminPart() {
        return "admin";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("userList", userServiceImp.getUsers());
        return "users";
    }

    @GetMapping("/users/new")
    public String addUser(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "newUser";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newUser";
        }
        userServiceImp.save(user);

        return "redirect:/admin/users";
    }

    @PatchMapping("/users/update")
    public String update(@RequestParam("userId") int id, Model model) {
        model.addAttribute("user", userServiceImp.getUser(id));

        return "newUser";
    }

    @DeleteMapping("/users/delete")
    public String delete(@RequestParam("userId") int id) {
        userServiceImp.delete(id);

        return "redirect:/admin/users";
    }
}