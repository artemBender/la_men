package com.la_shop.la_men.controllers.UserControllers;

import com.itextpdf.text.DocumentException;
import com.la_shop.la_men.Services.BasketService;
import com.la_shop.la_men.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final OrderService orderService;

    @GetMapping
    public String basket (Model model)
    {
        model.addAttribute("total", basketService.getTotalPrice());
        model.addAttribute("quantity", basketService.getQuantity());
        model.addAttribute("product", basketService.getAll());
        return "basket";
    }

    @PostMapping("/{id}")
    public String deleteFromBasket (@PathVariable(value = "id") Long id, Model model)
    {
        basketService.delete(id);
        return "redirect:/basket";
    }

    @PostMapping("/order")
    public String makeAnOrder (@RequestParam("state") String destination, @RequestParam("deliveryDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, Model model)
            throws DocumentException, IOException {
        orderService.makeAnOrder(destination, date);
        return "thanks";
    }

    @GetMapping ("/order")
    public String getOrders(Model model)
    {
        model.addAttribute("total", basketService.getTotalPrice());
        return "orders";
    }
}

