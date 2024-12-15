package com.la_shop.la_men.controllers.AdminControllers;

import com.la_shop.la_men.Entities.Products;
import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.Services.ProductService;
import com.la_shop.la_men.Services.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static com.la_shop.la_men.Services.UserService.setUser;

@Controller
@RequestMapping("/add")
@RequiredArgsConstructor
public class AddController {

    private final ProductService productService;
    private final SizeService sizeService;

    @GetMapping("/product")
    private String admin(Model model)
    {
        setUser(model);
        return "addProduct";
    }

    @PostMapping("/product")
    private String addProduct (Model model, @RequestParam("image") MultipartFile image, @RequestParam("name") String name, @RequestParam("composition") String composition, @RequestParam("description") String description, @RequestParam("price") double price, @RequestParam("sub_category") String sub_category)
    {
        setUser(model);
        model.addAttribute("productID", productService.create(name, description, composition, price, sub_category, image));
        return "addProduct";
    }

    @GetMapping("/size/{productID}")
    private String size(@PathVariable(value = "productID") String product_id, Model model)
    {
        setUser(model);
        model.addAttribute("product", productService.get(Long.valueOf(product_id)));
        model.addAttribute("productID", product_id);
        return "addSize";
    }

    @PostMapping("/size/{productID}")
    private String addSize(@PathVariable(value = "productID") String product_id, @RequestParam("size") String size, @RequestParam("quantity") int quantity, Model model)
    {
        setUser(model);
        model.addAttribute("product", productService.get(Long.valueOf(product_id)));
        model.addAttribute("productID", product_id);
        return sizeService.create(Long.valueOf(product_id), size, quantity, model);
    }





}
