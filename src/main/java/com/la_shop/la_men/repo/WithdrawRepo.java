package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Withdraw;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
public interface WithdrawRepo extends CrudRepository<Withdraw, Long> {
}
