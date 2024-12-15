package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Products;
import com.la_shop.la_men.Entities.Purchase;
import com.la_shop.la_men.repo.PurchaseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepo purchaseRepo;

    public void create(Products product, String size, double total, int quantity)
    {
        double price = total/quantity;
        Purchase purchase = new Purchase(product, Integer.parseInt(size), quantity, price, total);
        purchaseRepo.save(purchase);
    }
    public List<Purchase> get()
    {
        return (List<Purchase>) purchaseRepo.findAll();
    }
    public double getCost()
    {
        return purchaseRepo.GetSumCost();
    }

}
