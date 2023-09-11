package com.example.yecaoshi.controller;

import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsResponse;
import com.doudian.open.api.buyin_doukeOrderAds.data.OrdersItem;
import com.example.yecaoshi.control.OrderControl;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.Resp;
import com.example.yecaoshi.util.CommonUtil;
import com.example.yecaoshi.util.DouyinAPI;
import com.example.yecaoshi.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

@Controller
@RequestMapping("order")
@Slf4j

public class forOrder {
    @Autowired
    private HGoodsMapper hGoodsMapper;
    @Autowired
    private Resp resp;
    @Autowired
    private DouyinAPI douyinAPI;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OrderControl orderControl;
    @PostMapping("orderDetail")
    @ResponseBody
    public String web_getOrderDetail(@RequestParam(value = "orderId")String orderid) throws ParseException {
        BuyinDoukeOrderAdsResponse buyinDoukeOrderAdsResponse = orderControl.con_getOrderDetailById(orderid);
        OrdersItem ordersItem = buyinDoukeOrderAdsResponse.getData().getOrders().get(0);
        orderControl.con_handleDouOrder(ordersItem);
        return "cc";
    }
}
