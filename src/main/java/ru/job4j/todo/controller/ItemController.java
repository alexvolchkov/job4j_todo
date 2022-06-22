package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.ICategoryService;
import ru.job4j.todo.service.IItemService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ItemController {
    private final IItemService itemService;
    private final ICategoryService categoryService;

    public ItemController(IItemService itemService, ICategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("user", getUserFromSession(session));
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
    public String addItem(Model model, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        model.addAttribute("categories", categoryService.findAll());
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item,
                             @RequestParam("category.id") List<Integer> listId,
                             HttpSession session) {
        User user = getUserFromSession(session);
        item.setUser(user);
        for (Integer id : listId) {
            categoryService.findById(id).ifPresent(item::addCategory);
        }
        itemService.add(item);
        return "redirect:/index";
    }

    @GetMapping("/formItem/{itemId}")
    public String formItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id).orElse(new Item()));
        return "formItem";
    }

    @GetMapping("/done/{itemId}")
    public String done(@PathVariable("itemId") int id) {
        itemService.done(id);
        return "redirect:/index";
    }

    @GetMapping("/formUpdate/{itemId}")
    public String formUpdate(Model model, @PathVariable("itemId") int id) {
        model.addAttribute(itemService.findById(id).orElse(new Item()));
        return "update";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/index";
    }

    @GetMapping("/formDelete/{itemId}")
    public String formDelete(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/index";
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
