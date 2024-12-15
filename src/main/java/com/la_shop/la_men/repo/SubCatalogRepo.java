package com.la_shop.la_men.repo;

import com.la_shop.la_men.Entities.CatalogSubdivision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SubCatalogRepo extends CrudRepository<CatalogSubdivision, Long> {
    @Query ("select name from CatalogSubdivision where id = :id")
    String GetSubCatalogBySubID(@Param("id") Long id);

    @Query ("select id from CatalogSubdivision where name = :name")
    String GetSubID(@Param("name") String name);
    @Query("select cs.catalog_id from CatalogSubdivision cs where cs.id = :id")
    Long GetCatalogID (@Param("id") Long sub_id);

}
