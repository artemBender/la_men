package com.la_shop.la_men.controllers.AdminControllers;

import com.la_shop.la_men.Entities.Products;
import com.la_shop.la_men.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDate;

import static com.la_shop.la_men.Services.UserService.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final StorehouseService storehouseService;
    private final OrderService orderService;
    private final OrdersStatusService ordersStatusService;
    private final CatalogService catalogService;
    private final UserService userService;

    @PostMapping("/size/{productID}")
    private String orderSize(@PathVariable(value = "productID") Long product_id, @RequestParam("size") String size, @RequestParam("quantity") int quantity, @RequestParam("total") double total, Model model)
    {
        setUser(model);
        BALANCE -= total;
        userService.updateBalance(BALANCE, ADMIN_ID);
        Products product = productService.get(product_id);
        model.addAttribute("product", product);
        model.addAttribute("id", product_id);
        purchaseService.create(product, size, total, quantity);
        storehouseService.create(product_id, size, quantity);
        model.addAttribute("size", storehouseService.getAllSizes(product_id));
        return "redirect:/home/{productID}/about";
    }




    @PostMapping("/revenue/1")
    public  String revenue1 (Model model, @RequestParam("dateRange") String dateRange)
    {
        setUser(model);
        model.addAttribute("revenue", orderService.getRevenueInRange(dateRange));
        ordersStatusService.updateStatus();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        model.addAttribute("balance", decimalFormat.format(BALANCE));
        model.addAttribute("subcategories", catalogService.getAllSubs());
        model.addAttribute("orders", orderService.get());
        return "ordersView";
    }

    @PostMapping("/revenue/2")
    public  String revenue2 (Model model, @RequestParam("endDate") LocalDate endDate, @RequestParam("startDate") LocalDate startDate)
    {
        setUser(model);
        model.addAttribute("revenue", orderService.getRevenueInRange(startDate, endDate));
        ordersStatusService.updateStatus();
        model.addAttribute("subcategories", catalogService.getAllSubs());
        model.addAttribute("orders", orderService.get());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        model.addAttribute("balance", decimalFormat.format(BALANCE));
        return "ordersView";
    }

    @GetMapping("/view")
    public String ordersView (Model model)
    {
        ordersStatusService.updateStatus();
        setUser(model);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        model.addAttribute("balance", decimalFormat.format(BALANCE));
        model.addAttribute("subcategories", catalogService.getAllSubs());
        model.addAttribute("orders", orderService.get());
        return "ordersView";
    }

    @GetMapping("/sales")
    public String sales (Model model)
    {
        double revenue = orderService.getRevenue() - purchaseService.getCost();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        model.addAttribute("revenue", decimalFormat.format(revenue));
        model.addAttribute("balance", decimalFormat.format(BALANCE));
        model.addAttribute("sales", orderService.get());
        model.addAttribute("purchase", purchaseService.get());
        return "sales";
    }

}
