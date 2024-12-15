package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Products;
import jakarta.persistence.OrderBy;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface ProductRepo extends CrudRepository<Products, Long> {

    @Query("SELECT p, COALESCE(s.pr_count, 0) AS pr_count " +
            "FROM Products p " +
            "LEFT JOIN ( " +
            "    SELECT o.products.id as product_id, COUNT(*) AS pr_count " +
            "    FROM Orders o " +
            "    GROUP BY product_id " +
            ") s ON p.id = s.product_id " +
            "WHERE p.catalog_sub_id = :sub_id " +
            "ORDER BY pr_count DESC")
    List<Products> getProductsBySubId(@Param("sub_id") Long id);
    @Query("select p from Products p join Storehouse sh on sh.product_id = p.id where sh.quantity < 3")
    List <Products> GetShortageProducts ();
    @Query(value = "(SELECT p.* from what_to_wear_with ww " +
            "JOIN Products p on ww.product_id_to_wear_with = p.id " +
            "WHERE ww.product_id = :id) " +
            "UNION " +
            "SELECT pr.* FROM what_to_wear_with ww " +
            "JOIN products p ON ww.product_id_to_wear_with = p.id " +
            "JOIN products pr ON pr.id = ww.product_id " +
            "WHERE ww.product_id_to_wear_with = :id", nativeQuery = true)
    List<Products> WhatToWearWithByID(@Param("id") Long id);

    @Query("SELECT p FROM Products p WHERE p.name LIKE %:query% OR p.description LIKE %:query%")
    List<Products> Search(@Param("query") String query);

    @Query("select p from Products p where p.id = :id")
    Products GetProductById(@Param("id") Long id);

    @Query("SELECT p, COALESCE(s.pr_count, 0) AS pr_count " +
            "FROM Products p " +
            "LEFT JOIN ( " +
            "    SELECT o.products.id as product_id, COUNT(*) AS pr_count " +
            "    FROM Orders o " +
            "    GROUP BY product_id " +
            ") s ON p.id = s.product_id " +
            "WHERE p.price >= :min " +
            "AND p.price <= :max " +
            "AND p.catalog_sub_id = :id " +
            "AND (:material IS NULL OR p.composition LIKE %:material%) " +
            "ORDER BY pr_count DESC")
    List<Products> Filtration(@Param("min") double min,
                              @Param("max") double max,
                              @Param("material") String material,
                              @Param("id") Long id);
    @Transactional
    @Modifying
    @Query("delete from Products where id = :id")
    void DeleteProduct (@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Products set name = :name where id=:id")
    void UpdateName (@Param("name") String name, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Products set description = :description where id=:id")
    void UpdateDescription (@Param("description") String description, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Products set price = :price where id=:id")
    void UpdatePrice (@Param("price") double price, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Products set composition = :composition where id=:id")
    void UpdateComposition (@Param("composition") String composition, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Products set catalog_sub_id = :catalog_sub_id where id=:id")
    void UpdateSudCatalog (@Param("catalog_sub_id") String catalog_sub_id, @Param("id") Long id);

    @Query ("select catalog_sub_id from Products where id = :id")
    Long GetSubIDByProductID(@Param("id") Long id);

    @Query("select max(id) from Products")
    int GetMaxId();

    @Query("select min(id) from Products")
    int GetMinId();

    @Query("select max(price) from Products")
    double GetMaxPrice();

}