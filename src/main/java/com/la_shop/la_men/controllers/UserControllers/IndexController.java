package com.la_shop.la_men.controllers.UserControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.la_shop.la_men.Entities.*;
import com.la_shop.la_men.Services.BasketService;
import com.la_shop.la_men.Services.ProductService;
import com.la_shop.la_men.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import static com.la_shop.la_men.Services.UserService.setUser;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BasketService basketService;
    private final ProductService productService;
    private final UserService userService;


    @GetMapping("/")
    public String home(Model model) throws JsonProcessingException {
        setUser(model);
        userService.setBalance();
        model.addAttribute("shortage", productService.getShortage());
        model.addAttribute("cartItemCount", basketService.getQuantity());
        model.addAttribute("todayProducts", productService.getTodayProducts());
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Вход");
        return "login";
    }
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String reg(HttpServletRequest request, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String phone, @RequestParam String email, @RequestParam String password, Model model)
    {
        if (firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || password.isEmpty())
        {
            model.addAttribute("error_message_1", "Все поля должны быть заполнены");
            return "registration";
        }
        else if (userService.isExist(email) == 1)
        {
            model.addAttribute("error_message_2", "Пользователь уже зарегистрирован");
            return "registration";
        }
        userService.registerUser(request, firstname, lastname, email, phone, password);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String log(@RequestParam String email, @RequestParam String password, HttpServletRequest request, Model model)
    {

        if(userService.login(email, password, request).equals("login"))
        {
            model.addAttribute("error_message", "Ошибка ввода логина или пароля");
            return "login";
        }
        else return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout (HttpServletRequest request, Model model)
    {
        userService.logout(request);
        model.addAttribute("user", 0);
        model.addAttribute("todayProducts", productService.getTodayProducts());
        return "home";
    }

}
