package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.Catalogs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CatalogsRepo extends CrudRepository<Catalogs, Long> {
    @Query("select name from Catalogs where id = :id")
    String GetCatalogName(@Param("id") Long id);
}
