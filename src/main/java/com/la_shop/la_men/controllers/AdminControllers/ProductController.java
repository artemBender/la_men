package com.la_shop.la_men.controllers.AdminControllers;

import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.Services.CatalogService;
import com.la_shop.la_men.Services.ProductService;
import com.la_shop.la_men.Services.StorehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static com.la_shop.la_men.Services.UserService.setUser;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    private final CatalogService catalogService;
    private final StorehouseService storehouseService;
    @GetMapping("/{action}/{id}")
    public String updateProduct(@PathVariable(value = "action") String action, @PathVariable(value = "id") Long id, Model model)
    {
        setUser(model);
        Long sub_id = productService.getSubId(id);
        switch (action) {
            case "delete":
                productService.delete(id);
                return "redirect:/home/" + sub_id;
            case "edit":
                Long cat_id = catalogService.getCatalogId(sub_id);
                model.addAttribute("catalog", catalogService.getCatalogName(cat_id));
                model.addAttribute("subCatalog", catalogService.getSubCatalogName(sub_id));
                model.addAttribute("product", productService.get(id));
                return "productEditing";
            case "addSize":
                List<Storehouse> size  = storehouseService.getAllSizes(id);
                size.sort(Comparator.comparing(Storehouse::getSize));
                model.addAttribute("productID", id);
                model.addAttribute("size",size);
                model.addAttribute("product", productService.get(id));
                return "addSize";
        }
        return "redirect:/home/{id}/about";
    }

    @PostMapping("/edit/{id}")
    public String editProduct (@PathVariable(value = "id") Long id, @RequestParam("name") @DateTimeFormat(pattern = "yyyy-MM-dd") String name, @RequestParam("description") @DateTimeFormat(pattern = "yyyy-MM-dd") String description, @RequestParam("price") double price, @RequestParam("composition") String composition, @RequestParam("subCatalog") String sub_catalog, Model model)
    {
        setUser(model);
        productService.update(id, name, description, price, composition, sub_catalog);
        return "redirect:/home/{id}/about";
    }
}
