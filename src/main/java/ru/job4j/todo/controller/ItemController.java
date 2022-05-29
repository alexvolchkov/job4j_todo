package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.job4j.todo.service.IItemService;

@Controller
public class ItemController {
    private final IItemService itemService;

    public ItemController(IItemService itemService) {
        this.itemService = itemService;
    }

    public String index(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "index";
    }
}
