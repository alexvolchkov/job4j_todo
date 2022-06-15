package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    private IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String users(Model model, HttpSession session) {
        model.addAttribute("users", service.findAll());
        model.addAttribute("user", getUserFromSession(session));
        return "users";
    }

    @GetMapping("/formAddUser")
    public String addUser(Model model, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = service.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким именем уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        return "success";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        return "fail";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = service.findUserByNameAndPwd(
                user.getName(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

    private User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
