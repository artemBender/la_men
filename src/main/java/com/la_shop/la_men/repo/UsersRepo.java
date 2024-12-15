package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface UsersRepo extends CrudRepository<Users, Long> {

    @Query("SELECT count(*) from Users u WHERE u.email = :email AND u.user_password = :password")
    int LogIn(@Param("email") String email, @Param("password") String password);

    @Query ("Select user_password from Users where email = :email")
    String getPasswordUserByEmail(@Param("email") String email);
    @Query("select u.id from Users u where u.email = :email AND u.user_password = :password")
    Long GetUsersID(@Param("email") String email, @Param("password") String password);

    @Query("select u from Users u where u.id = :id")
    Users GetUserByID(@Param("id") Long id);


    @Transactional
    @Modifying
    @Query("update Users set firstname = :firstname where id=:id")
    void UpdateFirstname(@Param("firstname") String firstname, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Users set ransom = ransom + :total where id=:id")
    void UpdateRansom(@Param("total") double total, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Users set ransom = :total where id=:id")
    void UpdateBalance(@Param("total") double total, @Param("id") Long id);
    @Transactional
    @Modifying
    @Query("update Users set lastname = :lastname where id=:id")
    void UpdateLastname(@Param("lastname") String lastname, @Param("id") Long id);
    @Transactional
    @Modifying
    @Query("update Users set phone = :phone where id=:id")
    void UpdatePhone(@Param("phone") String phone, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Users set email = :email where id=:id")
    void UpdateEmail(@Param("email") String lastname, @Param("id") Long id);

    @Query("select count(*) from Users u where u.email = :email")
    int isExist(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("update Users set user_password = :new_password where email = :email")
    void UpdatePassword(@Param("new_password") String new_password, @Param("email") String email);

    @Query("SELECT CONCAT(firstname, ' ', lastname) AS concatenated_value FROM Users where id = :id")
    String GetFullName (@Param("id") Long id);

    @Query("select email from Users where id = :id")
    String GetEmail (@Param("id") Long id);
}
