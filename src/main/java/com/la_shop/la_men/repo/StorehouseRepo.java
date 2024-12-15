package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Storehouse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StorehouseRepo extends CrudRepository<Storehouse, Long> {

    @Query("SELECT s from Storehouse as s WHERE product_id = :id")
    List<Storehouse> GetSizeById(@Param("id") Long id);

    @Query("select sh from Storehouse sh where product_id = :id and size = :size")
    Storehouse GetBySizeAndProductID(@Param("id") Long id, @Param("size") String size);

    @Transactional
    @Modifying
    @Query("Update Storehouse SET quantity = quantity - 1 WHERE id = :id")
    void UpdateQuantity (@Param("id") Long sh_id);

    @Transactional
    @Modifying
    @Query("Update Storehouse SET quantity = quantity + 1 WHERE id = :id")
    void UpdateQuantity1 (@Param("id") Long sh_id);

    @Query ("select id from Storehouse where product_id = :product_id and size = :size")
    Long GetStorehouseId (@Param("product_id") Long product_id, @Param("size") String size);
    @Transactional
    @Modifying
    @Query("Update Storehouse SET quantity = quantity + :q WHERE id = :id")
    void IncreaseQuantity (@Param("id") Long sh_id, @Param("q") int quantity);
}
