package com.la_shop.la_men.controllers.UserControllers;


import com.la_shop.la_men.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.la_shop.la_men.Services.UserService.setUser;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final OrdersStatusService ordersStatusService;
    private final ProfileService profileService;
    private final BasketService basketService;
    private final OrderService orderService;
    private final UserService userService;
    private final FeedbackService feedbackService;

    @GetMapping
    public String profile (Model model)
    {
        ordersStatusService.updateStatus();
        setUser(model);
        model.addAttribute("user", userService.get(UserService.UserID));
        if (profileService.isExist(UserService.UserID)) {
            model.addAttribute("town", profileService.get(UserService.UserID).getHometown());
            model.addAttribute("birthday", profileService.get(UserService.UserID).getBirthday());
        }
        model.addAttribute("orders", orderService.get(UserService.UserID));
        model.addAttribute("basket" , basketService.isEmpty());
        return "profile";
    }

    @GetMapping("/{id}")
    public String profileView(@PathVariable(value = "id") Long id, Model model)
    {
        model.addAttribute("user", userService.get(id));
        if (profileService.isExist(UserService.UserID)) {
            model.addAttribute("town", profileService.get(UserService.UserID).getHometown());
            model.addAttribute("birthday", profileService.get(UserService.UserID).getBirthday());
        }
        return "profileView";
    }

    @PostMapping
    public String ApplyProfile(Model model, @RequestParam String firstname, @RequestParam String lastname,
                               @RequestParam String phone, @RequestParam String email, @RequestParam String town, @RequestParam String birthday)
    {
        setUser(model);
        userService.update(firstname, lastname, phone, email);
        profileService.update(birthday, town);
        return "redirect:/profile";
    }


    @PostMapping("/feedback")
    public String writeFeedback (@RequestParam("feedback") String feedback,@RequestParam("productId") Long productId)
    {
        feedbackService.create(UserService.UserID, productId, feedback);
        return "redirect:/profile";
    }


}
