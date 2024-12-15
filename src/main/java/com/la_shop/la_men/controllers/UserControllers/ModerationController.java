package com.la_shop.la_men.controllers.UserControllers;

import com.la_shop.la_men.Services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.la_shop.la_men.Services.UserService.setUser;


@Controller
@RequestMapping("/moderation")
@RequiredArgsConstructor
public class ModerationController {

    private final FeedbackService feedbackService;

    @GetMapping
    public String OpenModeration (Model model)
    {
        setUser(model);
        model.addAttribute("reviews", feedbackService.get());
        return "moderation";
    }

    @GetMapping("/{action}/{id}")
    public String moderation (Model model, @PathVariable(value = "action") String action, @PathVariable(value = "id") Long id)
    {
        setUser(model);
        feedbackService.moderate(action, id);
        return "redirect:/moderation";
    }

}
