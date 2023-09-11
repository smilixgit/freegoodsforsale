package com.example.yecaoshi.schedule;

import com.doudian.open.api.buyin_doukeOrderAds.data.OrdersItem;
import com.example.yecaoshi.control.OrderControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SaticScheduleTask {
    @Autowired
    OrderControl orderControl;
    //3.添加定时任务
//    @Scheduled(cron = "0/1 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws ParseException {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        log.info("执行静态定时任务时间: " + LocalDateTime.now());
        Date end = new Date();
        Date start=new Date(end.getTime()-1000*30);
        List<OrdersItem> ordersItems = orderControl.con_getOrderByTimeRange(start, end);
        for(OrdersItem ordersItem:ordersItems )
            orderControl.con_handleDouOrder(ordersItem);
    }
}
