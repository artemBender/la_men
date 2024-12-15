package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.repo.StorehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorehouseService {

    private final StorehouseRepo storehouseRepo;

    public List<Storehouse> getAllSizes (Long prod_id)
    {
        return storehouseRepo.GetSizeById(prod_id);
    }

    public Storehouse getByProductAndSize (Long prod_id, String size)
    {
        return storehouseRepo.GetBySizeAndProductID(prod_id, size);
    }

    public void increaseQuantity (Long strhs_id)
    {
        storehouseRepo.UpdateQuantity(strhs_id);
    }

    public void create(Long product_id, String size, int quantity)
    {
        List<Storehouse> checkSize = storehouseRepo.GetSizeById(product_id);
        for(Storehouse storehouse: checkSize)
        {
            if(storehouse.getSize() == Integer.parseInt(size))
            {
                storehouseRepo.IncreaseQuantity(storehouseRepo.GetStorehouseId(product_id, size), quantity);
                return;
            }
        }
        Storehouse storehouse = new Storehouse(product_id, Integer.parseInt(size), quantity);
        storehouseRepo.save(storehouse);
    }


}
