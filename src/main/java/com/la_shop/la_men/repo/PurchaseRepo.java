package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepo extends CrudRepository<Purchase, Long> {
    @Query("select sum(cost) from Purchase")
    double GetSumCost();
}
