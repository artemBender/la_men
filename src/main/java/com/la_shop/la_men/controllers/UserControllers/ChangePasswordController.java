package com.la_shop.la_men.controllers.UserControllers;

import com.la_shop.la_men.Services.MailService;
import com.la_shop.la_men.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
@RequestMapping("/changePassword")
@RequiredArgsConstructor
public class ChangePasswordController {

    int CODE = 0;
    String RECOVERY_EMAIL = "";

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/enterEmail")
    public String forgetPassword ()
    {
        return "enterEmail";
    }

    @PostMapping("/enterEmail")
    public String getEmail(@RequestParam("email") String email, Model model)
    {
        if(userService.isExist(email) != 1)
        {
            model.addAttribute("error_message", "Почта введена не верно");
            return "enterEmail";
        }
        RECOVERY_EMAIL = email;
        Random generateCode = new Random();
        CODE = generateCode.nextInt(1000, 9999);
        mailService.sendEmail(email, "Код восстановления", String.valueOf(CODE));
        return "getCode";
    }

    @GetMapping("/getCode")
    public String getCode()
    {
        return "getCode";
    }

    @PostMapping("/getCode")
    public String enterCode(@RequestParam("code") int code, Model model)
    {
        if(code != CODE)
        {
            model.addAttribute("error_message", "Код введен не верно");
            return "getCode";
        } else return "forgetPassword";
    }

    @PostMapping
    public String changePassword (@RequestParam("password") String password, @RequestParam("repeat_password") String repeat_password, Model model)
    {
        if(!password.equals(repeat_password))
        {
            model.addAttribute("error_message", "Пароли должны совпадать");
            return "forgetPassword";
        }
        userService.update(password, RECOVERY_EMAIL);
        return "login";
    }

}
