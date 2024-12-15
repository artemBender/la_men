package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Feedback;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepo extends CrudRepository<Feedback, Long> {
    @Query("select f from Feedback f where product_id = :id")
    List<Feedback> GetFeedbackByProductID(@Param("id") Long id);
}
