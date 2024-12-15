package com.la_shop.la_men.Services;


import com.la_shop.la_men.Entities.Withdraw;
import com.la_shop.la_men.repo.WithdrawRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.la_shop.la_men.Services.UserService.BALANCE;

@Service
@RequiredArgsConstructor
public class WithdrawService {

    private final WithdrawRepo withdrawRepo;

    public void create(double amount, String destination)
    {
        Withdraw withdraw = new Withdraw(amount, destination);
        withdrawRepo.save(withdraw);
        BALANCE -= amount;
    }

    public List<Withdraw> get()
    {
        return (List<Withdraw>) withdrawRepo.findAll();
    }

}
