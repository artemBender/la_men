package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Feedback;
import com.la_shop.la_men.Entities.Moderation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ModerationRepo extends CrudRepository<Moderation, Long> {

    @Transactional
    @Modifying
    @Query("delete from Moderation where id = :id")
    void DeleteFeedback (@Param("id") Long id);

    @Query ("select m from Moderation m where id = :id")
    Moderation GetFeedback (@Param("id") Long id);
}
