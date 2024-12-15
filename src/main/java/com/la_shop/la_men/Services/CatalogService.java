package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.CatalogSubdivision;
import com.la_shop.la_men.repo.CatalogsRepo;
import com.la_shop.la_men.repo.SubCatalogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {


    private final SubCatalogRepo subCatalogRepo;
    private final CatalogsRepo catalogsRepo;

    public String getSubCatalogName (Long sub_id)
    {
        return subCatalogRepo.GetSubCatalogBySubID(sub_id);
    }

    public Long getCatalogId (Long sub_id)
    {
        return subCatalogRepo.GetCatalogID(sub_id);
    }

    public String getCatalogName (Long cat_id)
    {
        return catalogsRepo.GetCatalogName(cat_id);
    }


    public List<CatalogSubdivision> getAllSubs()
    {
        return (List<CatalogSubdivision>) subCatalogRepo.findAll();
    }
}
