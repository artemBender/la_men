package com.la_shop.la_men.controllers.UserControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.la_shop.la_men.Entities.Products;
import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.la_shop.la_men.Services.UserService.setUser;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final StorehouseService storehouseService;
    private final FeedbackService feedbackService;
    private final BasketService basketService;
    private final CatalogService catalogService;
    private final UserService userService;

    private int basketQuantity;
    private List<Products> currentListProducts;


    @GetMapping
    public String home(Model model) throws JsonProcessingException {
        setUser(model);
        userService.setBalance();
        model.addAttribute("shortage", productService.getShortage());
        model.addAttribute("cartItemCount", basketQuantity);
        model.addAttribute("todayProducts", productService.getTodayProducts());
        return "home";
    }


    @GetMapping("/{id}")
    public String getProducts(Model model,
                              @PathVariable(value = "id") Long id,
                              @RequestParam(required = false) String query,
                              @RequestParam(required = false) String sort,
                              @RequestParam(required = false) String material,
                              @RequestParam(required = false) String minPrice,
                              @RequestParam(required = false) String maxPrice)
    {
        setUser(model);
        currentListProducts = productService.getSubProducts(id);
        currentListProducts = productService.subCatalogFiltration(minPrice, maxPrice, material, id);
        if(query != null)
            currentListProducts = productService.searchInProdList(currentListProducts, query);
        if(sort != null)
            currentListProducts = productService.sort(currentListProducts, sort);
        model.addAttribute("subCatalogName", catalogService.getSubCatalogName(id));
        model.addAttribute("sub_id", id);
        model.addAttribute("catalog_id", catalogService.getCatalogId(id));
        model.addAttribute("sub", currentListProducts);
        model.addAttribute("cartItemCount", basketQuantity);
        return "catalogSubdivision";
    }


    @GetMapping("/{id}/about")
    public String itemPage(@PathVariable(value = "id") Long id, Model model)
    {
        setUser(model);
        List<Storehouse> allSizes = storehouseService.getAllSizes(id);
        allSizes.sort(Comparator.comparing(Storehouse::getSize));
        model.addAttribute("subCatalogName", productService.getSubCatalogName(id));
        model.addAttribute("size", allSizes);
        model.addAttribute("feedbacks", feedbackService.getComments(id));
        model.addAttribute("product", productService.get(id));
        model.addAttribute("products",  productService.getWWWList(id));
        model.addAttribute("cartItemCount", basketQuantity);
        return "itemPage";
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model)
    {
        setUser(model);
        currentListProducts = productService.searchProducts(query);
        model.addAttribute("products", currentListProducts);
        model.addAttribute("cartItemCount", basketQuantity);
        return "search";
    }


    @GetMapping("/search/{sort}")
    public String SearchSort (@PathVariable(value = "sort") String sort, Model model)
    {
        setUser(model);
        switch (sort)
        {
            case "asc":
            {
                currentListProducts.sort(Comparator.comparingDouble(Products::getPrice));
            }
            break;
            case "desc":
            {
                currentListProducts.sort(Comparator.comparingDouble(Products::getPrice).reversed());
            }
            break;
        }
        model.addAttribute("products", currentListProducts);
        model.addAttribute("cartItemCount", basketService.getQuantity());

        return "search";
    }


    @PostMapping("/{id}/about")
    public String addToBasket(@PathVariable(value = "id") Long id, @RequestParam("size") String size, Model model)
    {
        setUser(model);
        Storehouse storehouse = storehouseService.getByProductAndSize(id, size);
        basketService.save(productService.get(id), storehouse);
        storehouseService.increaseQuantity(storehouse.getId());
        basketQuantity =  basketService.getQuantity();
        return "redirect:/home/{id}/about";
    }


    @GetMapping("/{id}/{sort}")
    public String CatalogSort (@PathVariable(value = "id") Long id,@PathVariable(value = "sort") String sort, Model model)
    {
        setUser(model);
        switch (sort)
        {
            case "esc":
            {
                currentListProducts.sort(Comparator.comparingDouble(Products::getPrice));
            }
            break;
            case "desc":
            {
                currentListProducts.sort(Comparator.comparingDouble(Products::getPrice).reversed());
            }
            break;
        }
        model.addAttribute("catalog_id", catalogService.getCatalogId(id));
        model.addAttribute("sub_id", id);
        model.addAttribute("subCatalogName", catalogService.getSubCatalogName(id));
        model.addAttribute("sub", currentListProducts);
        model.addAttribute("cartItemCount", basketQuantity);
        return "catalogSubdivision";
    }

}
