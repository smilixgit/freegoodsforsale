package com.example.yecaoshi.control;

import com.example.yecaoshi.mapper.HOrderMapper;
import com.example.yecaoshi.util.DouyinAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderControl {
  @Autowired
  HOrderMapper hOrderMapper;
  @Autowired
  DouyinAPI douyinAPI;
  //抓取订单，每隔一秒访问
  public String getOrderByTimeRange() throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    Date end = new Date();
    Date start=new Date(end.getTime()-1000*30);

    String end_time = simpleDateFormat.format(end);// new Date()为获取当前系统时间，也可使用当前时间戳
    String  start_time=simpleDateFormat.format(start);
    String cesho=douyinAPI.api_getOrderByTimeRange(start_time,end_time,"no");



    return cesho;
  }
}
