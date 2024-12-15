package com.la_shop.la_men.controllers.AdminControllers;

import com.la_shop.la_men.Entities.Withdraw;
import com.la_shop.la_men.Services.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;

import static com.la_shop.la_men.Services.UserService.BALANCE;

@Controller
@RequestMapping("/withdraw")
@RequiredArgsConstructor
public class WithdrawController {


    private final WithdrawService withdrawService;

    @PostMapping("/add")
    public String addWithdraw(Model model, @RequestParam("destination") String destination, @RequestParam("amount") double amount) {
        if (BALANCE > amount) {
            withdrawService.create(amount, destination);
            model.addAttribute("successMessage", "Операция выполнена успешно");
        } else {
            model.addAttribute("failureMessage", "Ошибка операции: операция превышает баланс");
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        model.addAttribute("balance", decimalFormat.format(BALANCE));
        model.addAttribute("withdraw", withdrawService.get());
        return "withdraw";
    }

    @GetMapping
    public String withdraw(Model model)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        model.addAttribute("balance", decimalFormat.format(BALANCE));
        model.addAttribute("withdraw", withdrawService.get());
        return "withdraw";
    }

}
