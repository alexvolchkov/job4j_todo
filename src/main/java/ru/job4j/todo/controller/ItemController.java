package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.IItemService;

@Controller
public class ItemController {
    private final IItemService itemService;

    public ItemController(IItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "index";
    }

    @GetMapping("/newItems")
    public String newItems(Model model) {
        model.addAttribute("items", itemService.findAll(false));
        return "index";
    }

    @GetMapping("/closeItems")
    public String closeItems(Model model) {
        model.addAttribute("items", itemService.findAll(true));
        return "index";
    }

    @GetMapping("/formAddItem")
    public String addItem(Model model) {
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        itemService.add(item);
        return "redirect:/index";
    }

    @GetMapping("/formItem/{itemId}")
    public String formItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id).get());
        return "formItem";
    }

    @GetMapping("/done")
    public String done(@ModelAttribute Item item) {
        itemService.findById(item.getId()).ifPresent(el -> {
            el.setDone(true);
            System.out.println(el);
            itemService.update(el);
        });
        return "redirect:/index";
    }

    @GetMapping("/formUpdate")
    public String formUpdate(Model model, @ModelAttribute Item item) {
        itemService.findById(item.getId()).ifPresent(el -> model.addAttribute("item", el));
        return "update";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/index";
    }

    @GetMapping("/formDelete")
    public String formDelete(@ModelAttribute Item item) {
        itemService.delete(item.getId());
        return "redirect:/index";
    }
}