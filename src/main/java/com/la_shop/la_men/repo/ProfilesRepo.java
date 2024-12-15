package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Profiles;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProfilesRepo extends CrudRepository<Profiles, Long> {
    @Query("select id from Profiles where user_id = :id")
    Long GetProfileID (@Param("id") Long id);
    @Transactional
    @Modifying
    @Query("update Profiles set hometown = :town where user_id=:id")
    void UpdateHometown(@Param("town") String town, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Profiles set birthday = :birthday where user_id=:id")
    void UpdateBirthday(@Param("birthday") String birthday, @Param("id") Long id);
}
