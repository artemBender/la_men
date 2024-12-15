package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.repo.StorehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeService {

    private final StorehouseRepo storehouseRepo;

    public String create(Long product_id, String size, Integer quantity, Model model)
    {
        List<Storehouse> checkSize = storehouseRepo.GetSizeById(product_id);
        List<Storehouse> AllSizes = storehouseRepo.GetSizeById(product_id);
        AllSizes.sort(Comparator.comparing(Storehouse::getSize));
        for(Storehouse storehouse: checkSize)
        {
            if(storehouse.getSize() == Integer.parseInt(size))
            {
                model.addAttribute("size", AllSizes);
                storehouseRepo.IncreaseQuantity(storehouseRepo.GetStorehouseId(product_id, size), quantity);
                model.addAttribute("error_message", "Размер уже внесён, количество увеличено");
                return "addSize";
            }
        }
        Storehouse storehouse = new Storehouse(product_id, Integer.parseInt(size), quantity);
        storehouseRepo.save(storehouse);
        AllSizes = storehouseRepo.GetSizeById(product_id);
        AllSizes.sort(Comparator.comparing(Storehouse::getSize));
        model.addAttribute("size", AllSizes);
        return "addSize";
    }

}
