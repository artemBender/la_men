package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Basket;
import com.la_shop.la_men.Entities.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepo extends CrudRepository<Basket, Long> {

    @Query("select b from Basket b")
    List<Basket> GetBasket ();

    @Query("select count(*) from Basket")
    int GetQuantity ();

    @Transactional
    @Modifying
    @Query ("delete from Basket where id = :id")
    void DeleteFromBasket (@Param("id") Long id);

    @Query("select b from Basket b where id = :id")
    Basket GetProductByID (@Param("id") Long id);
    @Query("SELECT SUM(p.price) FROM Basket b JOIN b.products p")
    Double TotalPrice();

    @Query("SELECT b.products FROM Basket b")
    List<Products> GetProducts ();
}

