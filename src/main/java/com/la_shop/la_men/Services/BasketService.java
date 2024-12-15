package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Basket;
import com.la_shop.la_men.Entities.Products;
import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.repo.BasketRepo;
import com.la_shop.la_men.repo.StorehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepo basketRepo;
    private final StorehouseRepo storehouseRepo;

    public void save(Products product, Storehouse storehouse)
    {
        Basket basket = new Basket(product, storehouse);
        basketRepo.save(basket);
    }

    public int getQuantity()
    {
        return basketRepo.GetQuantity();
    }

    public String getTotalPrice()
    {
        String total;
        Iterable<Basket> basket = basketRepo.GetBasket();
        if (basketRepo.TotalPrice() == null) {
            total = "0";
        }
        else {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            total = decimalFormat.format(basketRepo.TotalPrice());

        }
        return total;
    }

    public void delete(Long basket_id)
    {
        storehouseRepo.UpdateQuantity1(basketRepo.GetProductByID(basket_id).getStorehouse().getId());
        basketRepo.DeleteFromBasket(basket_id);
    }


    public List<Basket> getAll()
    {
        return basketRepo.GetBasket();
    }


    public void clean()
    {
        List<Basket> baskets = basketRepo.GetBasket();
        for(Basket basket : baskets)
        {
            storehouseRepo.UpdateQuantity1(basket.getStorehouse().getId());
            basketRepo.DeleteFromBasket(basket.getId());
        }
    }

    public boolean isEmpty()
    {
        return basketRepo.GetBasket().isEmpty();
    }

}
