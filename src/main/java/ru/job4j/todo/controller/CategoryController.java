package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.service.ICategoryService;
import ru.job4j.todo.store.CommonMetods;

import javax.servlet.http.HttpSession;

@Controller
public class CategoryController {
    private final ICategoryService service;

    public CategoryController(ICategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpSession session) {
        model.addAttribute("categories", service.findAll());
        model.addAttribute("user", CommonMetods.getUserFromSession(session));
        return "categories";
    }

    @GetMapping("/formAddCategory")
    public String formAddCategory(Model model, HttpSession session) {
        model.addAttribute("user", CommonMetods.getUserFromSession(session));
        return "addCategory";
    }

    @PostMapping("/createCategory")
    public String createCategory(@ModelAttribute Category category) {
        service.add(category);
        return "redirect:/categories";
    }
}
