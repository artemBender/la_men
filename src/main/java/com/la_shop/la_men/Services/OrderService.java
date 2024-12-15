package com.la_shop.la_men.Services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.la_shop.la_men.Entities.Basket;
import com.la_shop.la_men.Entities.Orders;
import com.la_shop.la_men.Entities.Storehouse;
import com.la_shop.la_men.repo.BasketRepo;
import com.la_shop.la_men.repo.OrdersRepo;
import com.la_shop.la_men.repo.StorehouseRepo;
import com.la_shop.la_men.repo.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

import static com.la_shop.la_men.Services.UserService.UserID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final BasketRepo basketRepo;
    private final StorehouseRepo storehouseRepo;
    private final OrdersRepo ordersRepo;
    private final MailService mailService;
    private final UsersRepo usersRepo;
    private static final int MIN_VALUE = 100000;
    private static final int MAX_VALUE = 999999;

    public List<Orders> get(Long user_id)
    {
        List<Orders> orders = ordersRepo.GetOrderByUserID(UserID);
        orders.sort(Comparator.comparing(Orders::getCreated_at).reversed());
        return orders;
    }

    public List<Orders> get()
    {
        return (List<Orders>) ordersRepo.findAll();
    }

    public String getRevenueInRange(LocalDate startDate, LocalDate endDate)
    {
        Double total = ordersRepo.GetRevenueInRange(startDate, endDate);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(total);
    }

    public double getRevenue()
    {
        return ordersRepo.GetRevenue();
    }

    public String getRevenueInRange(String dateRange)
    {
        Double total = 0D;
        switch (dateRange)
        {
            case "today":
            {
                total = ordersRepo.GetRevenueInRange(LocalDate.now(), LocalDate.now());
            }
            break;
            case "week":
            {
                total = ordersRepo.GetRevenueInRange(LocalDate.now().minusDays(7), LocalDate.now());
            }
            break;
            case "month":
            {
                total = ordersRepo.GetRevenueInRange(LocalDate.now().minusDays(30), LocalDate.now());
            }
            break;
            case "year":
            {
                total = ordersRepo.GetRevenueInRange(LocalDate.now().minusDays(365), LocalDate.now());
            }
            break;
            case "allTime":
            {
                total = ordersRepo.GetAvgCheck();
            }
            break;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(total);
    }

    public void makeAnOrder (String destination, LocalDate deliveryDate) throws DocumentException, IOException {
        double totalPrice = 0;
        for(int i = 0; i < basketRepo.GetQuantity(); i++) {
            List<Basket> basket = (List<Basket>) basketRepo.findAll();
            Optional<Storehouse> storehouse = storehouseRepo.findById(basket.get(i).getStorehouse().getId());
            totalPrice+=basket.get(i).getProducts().getPrice();
            storehouseRepo.UpdateQuantity(storehouse.get().getId());
            int check = generateSixDigitNumber();
            Orders order = new Orders(UserID, basketRepo.GetProducts().get(i), 1, check, String.valueOf(basketRepo.GetBasket().get(i).getStorehouse().getSize()), deliveryDate, destination, "Собирается на складе");
            ordersRepo.save(order);
        }
        usersRepo.UpdateRansom(totalPrice, UserID);
        byte[] pdfBytes = generateBookingPdf();
        mailService.sendPdfByEmail(usersRepo.GetEmail(UserID), "Заказ принят", "Подробности заказа указаны в PDF-файле:", pdfBytes, "Мягкий чек заказа");
        basketRepo.deleteAll();
    }

    public List<Orders> getDeliveredOrders()
    {
        return ordersRepo.GetDeliveredOrders();
    }

    public static int generateSixDigitNumber() {
        Random random = new Random();
        return MIN_VALUE + random.nextInt(MAX_VALUE);
    }

    public byte[] generateBookingPdf() throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setInitialLeading(16);

            // Установка шрифта для кириллицы
            BaseFont baseFont = BaseFont.createFont("src/main/resources/font.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 14);

            // Открытие документа
            document.open();
            document.add(new Paragraph(usersRepo.GetFullName(UserID) + ", Ваш заказ подтвержден", font));
            document.add(new Paragraph("Итого товаров: " + basketRepo.GetQuantity(), font));
            List <Orders> orderProducts = ordersRepo.GetLastOrderedProducts(UserID, LocalDate.now());
            for (Orders orders : orderProducts) {
                document.add(new Paragraph("--------------------------------------------------", font)); //full name
                document.add(new Paragraph(orders.getProducts().getDescription()  + " " + orders.getProducts().getName(), font)); //full name
                document.add(new Paragraph("Цена: " + orders.getProducts().getPrice() + " руб.", font)); //price
                if(!orders.getSize().equals("0"))
                    document.add(new Paragraph( "Размер: " + orders.getSize(), font));//size
            }
            document.add(new Paragraph("--------------------------------------------------", font)); //full name
            document.add(new Paragraph("Дата доставки: " + orderProducts.getFirst().getDelivery_date(), font));
            document.add(new Paragraph("Адрес: " + orderProducts.getFirst().getDestination(), font));
            document.add(new Paragraph("Срок хранения в днях: 2", font));
            document.add(new Paragraph("Сумма заказа: " + basketRepo.TotalPrice(), font));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new IOException("Error creating PDF file", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }

}
