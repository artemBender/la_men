package com.la_shop.la_men.Services;

import com.la_shop.la_men.Entities.Orders;
import com.la_shop.la_men.repo.OrdersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersStatusService {

    private final OrdersRepo ordersRepo;

    public void updateStatus ()
    {
        List<Orders> orders = (List<Orders>) ordersRepo.findAll();
        for(Orders order : orders)
        {
            LocalDate curDate = LocalDate.now();
            Date currentDate = java.sql.Date.valueOf(curDate);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(java.sql.Date.valueOf(order.getCreated_at()));
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(java.sql.Date.valueOf(order.getDelivery_date()));
            long startTimeInMillis = startCalendar.getTimeInMillis();
            long endTimeInMillis = endCalendar.getTimeInMillis();
            long midpointTimeInMillis = (startTimeInMillis + endTimeInMillis) / 2;
            Calendar midpointCalendar = Calendar.getInstance();
            midpointCalendar.setTimeInMillis(midpointTimeInMillis);
            Date midpointDate = midpointCalendar.getTime();
            if (currentDate.after(java.sql.Date.valueOf(order.getDelivery_date())) || currentDate.equals(java.sql.Date.valueOf(order.getDelivery_date()))) {
                order.setStatus("Доставлено");
            } else if(midpointDate.after(currentDate) && midpointDate.before(java.sql.Date.valueOf(order.getDelivery_date())))
            {
                order.setStatus("Собирается на складе");
            }
            else if (midpointDate.before(currentDate) && midpointDate.after(java.sql.Date.valueOf (order.getCreated_at())))
            {
                order.setStatus("Отправлен в службу доставки");
            }
            ordersRepo.UpdateStatus(order.getStatus(), order.getId());
        }

    }



}
