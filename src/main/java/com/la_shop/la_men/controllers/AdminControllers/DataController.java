package com.la_shop.la_men.controllers.AdminControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.la_shop.la_men.Entities.Orders;
import com.la_shop.la_men.Entities.what_to_wear_with;
import com.la_shop.la_men.Services.OrderService;
import com.la_shop.la_men.Services.WWWService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DataController {

    private final WWWService wwwService;
    private final OrderService orderService;

    @PostMapping("/import")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        String message_success = null;
        String message_error = null;
        if (!file.isEmpty()) {
            try {
                wwwService.save(file);
                System.out.println("Данные успешно сохранены в базу данных.");
                message_success = "Данные успешно сохранены в базу данных.";
            } catch (Exception e) {
                e.printStackTrace();
                message_error = "Ошибка при импорте данных";
            }
        }
        model.addAttribute("message_success", message_success);
        model.addAttribute("message_error", message_error);
        return "addProduct";
    }


    @GetMapping("/export")
    public void exportOrdersToExcel(HttpServletResponse response) {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");

        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Orders");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Номер заказа");
        headerRow.createCell(1).setCellValue("Номер пользователя");
        headerRow.createCell(2).setCellValue("Название товара");
        headerRow.createCell(3).setCellValue("Описание товара");
        headerRow.createCell(4).setCellValue("Стоимость, бел. руб.");
        headerRow.createCell(5).setCellValue("Дата заказа");

        List<Orders> ordersList = orderService.getDeliveredOrders();
        // Create the data rows
        int rowNum = 1;
        CellStyle dateCellStyle = workbook.createCellStyle();
        short dateFormat = workbook.createDataFormat().getFormat("dd.MM.yyyy"); // Customize the date format as per your needs
        dateCellStyle.setDataFormat(dateFormat);
        for (Orders order : ordersList) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(order.getId());
            dataRow.createCell(1).setCellValue(order.getUser_id());
            dataRow.createCell(2).setCellValue(order.getProducts().getName());
            dataRow.createCell(3).setCellValue(order.getProducts().getDescription());
            dataRow.createCell(4).setCellValue(order.getProducts().getPrice());
            Cell dateCell = dataRow.createCell(5);
            dateCell.setCellValue(order.getCreated_at());
            dateCell.setCellStyle(dateCellStyle);
        }

        // Write the workbook to the response output stream
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
