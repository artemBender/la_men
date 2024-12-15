package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrdersRepo extends CrudRepository<Orders, Long> {

   @Query("select o from Orders o where user_id = :id")
   List<Orders> GetOrderByUserID (@Param("id") Long id);
   @Query("select sum(o.products.price) from Orders o where o.status = 'Доставлено'")
   double GetRevenue();
   @Transactional
   @Modifying
   @Query("update Orders set status = :status where id=:id")
   void UpdateStatus (@Param("status") String status, @Param("id") Long id);

   @Query("SELECT COALESCE(AVG(subquery.average_price), 0) AS average_price\n" +
           "FROM (\n" +
           "    SELECT AVG(o.products.price) AS average_price\n" +
           "    FROM Orders o\n" +
           "    WHERE o.status = 'Доставлено' AND o.delivery_date BETWEEN :startDate AND :endDate\n" +
           "    GROUP BY o.checkNumber\n" +
           ") AS subquery")
   double GetRevenueInRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

   @Query ("SELECT AVG(subquery.average_price) AS average_price\n" +
           "FROM (\n" +
           "    SELECT AVG(o.products.price) AS average_price\n" +
           "    FROM Orders o\n" +
           "    GROUP BY o.checkNumber\n" +
           ") AS subquery")
   double GetAvgCheck();
   @Query ("select distinct o from Orders o join Basket b on o.products.id = b.products.id where o.user_id = :id and o.created_at = :date")
   List <Orders> GetLastOrderedProducts(@Param("id") Long user_id, @Param("date") LocalDate date);

   @Query("select o from Orders o where status = 'Доставлено'")
   List<Orders> GetDeliveredOrders();
   }
