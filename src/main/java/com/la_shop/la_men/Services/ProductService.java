package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Products;
import com.la_shop.la_men.repo.ProductRepo;
import com.la_shop.la_men.repo.SubCatalogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static java.lang.Double.parseDouble;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepo productRepo;
    private final SubCatalogRepo subCatalogRepo;

    public void delete(Long prod_id)
    {
        productRepo.DeleteProduct(prod_id);
    }

    public Long getSubId(Long prod_id)
    {
        return productRepo.GetSubIDByProductID(prod_id);
    }

    public Long create(String name, String description, String composition, Double price, String sub_category, MultipartFile image)
    {
        byte[] p_image;
        try {
            p_image = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Products products = new Products(name, description, composition, price, Long.valueOf(sub_category), p_image);
        productRepo.save(products);

        return products.getId();
    }
    public double getMaxProductPrice()
    {
        return productRepo.GetMaxPrice();
    }

    public List<Products> subCatalogFiltration (String minPrice, String maxPrice, String material, Long sub_id)
    {
        if(minPrice != null && maxPrice != null)
            if(!minPrice.isEmpty() && !maxPrice.isEmpty())
                if(parseDouble(minPrice)>parseDouble(maxPrice))
                {
                    String temp = minPrice;
                    minPrice = maxPrice;
                    maxPrice = temp;
                }
        if (minPrice == null || Objects.requireNonNull(minPrice).isEmpty())
            minPrice = "0";
        if (maxPrice == null ||  Objects.requireNonNull(maxPrice).isEmpty())
            maxPrice = String.valueOf(this.getMaxProductPrice());
        return productRepo.Filtration(parseDouble(minPrice), parseDouble(maxPrice), material, sub_id);
    }

    public List<Products> getWWWList (Long prod_id)
    {
        return productRepo.WhatToWearWithByID(prod_id);
    }

    public Products get (Long prod_id)
    {
        return  productRepo.GetProductById(prod_id);
    }
    public List<Products> searchProducts (String query)
    {
        return productRepo.Search(query);
    }



    public String getSubCatalogName (Long prod_id)
    {
        return subCatalogRepo.GetSubCatalogBySubID(productRepo.GetSubIDByProductID(prod_id));
    }

    public List<Products> getSubProducts (Long sub_id)
    {
        return productRepo.getProductsBySubId(sub_id);
    }

    public List<Products> getTodayProducts()
    {
        Random randomId = new Random();
        Set<Long> uniqueIds = new HashSet<>();
        List<Products> todayProducts = new ArrayList<>();
        while (uniqueIds.size() < 4) {
            Long id;
            do {
                id = (long) randomId.nextInt(productRepo.GetMinId(), productRepo.GetMaxId());
            } while (!uniqueIds.add(id) || productRepo.GetProductById(id) == null);

            todayProducts.add(productRepo.GetProductById(id));
        }
        return todayProducts;
    }

    public List<Products> searchInProdList (List<Products> products, String query)
    {
        List<Products> searchProducts = new ArrayList<>();
        for(Products prod : products)
        {
            int isExistName = prod.getName().toLowerCase().indexOf(query.toLowerCase());
            int isExistDescription = prod.getDescription().toLowerCase().indexOf(query.toLowerCase());
            if(isExistDescription != -1 || isExistName != -1)
                searchProducts.add(prod);
        }
        return searchProducts;
    }

    public List<Products> sort (List<Products> productsList, String sort)
    {
        switch (sort)
        {
            case "asc":
            {
                productsList.sort(Comparator.comparingDouble(Products::getPrice));
            }
            break;
            case "desc":
            {
                productsList.sort(Comparator.comparingDouble(Products::getPrice).reversed());
            }
            break;
        }
        return productsList;
    }

    public List<Products> getShortage()
    {
        return productRepo.GetShortageProducts();
    }


    public void update(Long id, String name, String description, double price, String composition, String sub_catalog)
    {
        productRepo.UpdateName(name, id);
        productRepo.UpdateDescription(description, id);
        productRepo.UpdatePrice(price, id);
        productRepo.UpdateComposition(composition, id);
        productRepo.UpdateSudCatalog(subCatalogRepo.GetSubID(sub_catalog), id);
    }
}
