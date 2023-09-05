package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HGoods;
import com.example.yecaoshi.pojo.resp;
import com.example.yecaoshi.util.DouyinAPI;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ru.INN;
import org.hibernate.validator.constraints.time.DurationMax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("goods")
@Slf4j

public class forGoods {
    @Autowired
    private HGoodsMapper hGoodsMapper;
    @Autowired
    private com.example.yecaoshi.pojo.resp resp;
    @Autowired
    private DouyinAPI douyinAPI;
    @RequestMapping("getDouGoods")
    @ResponseBody
    public String getAllGoods()
    {
        List<HGoods> AllGoods=null;
        try {
            QueryWrapper<HGoods> queryWrapper=new QueryWrapper<>();
            queryWrapper.
                    eq("status",2);
             AllGoods=hGoodsMapper.selectList(queryWrapper);
        }
        catch (Exception exception)
        {
            resp.setCode(0);
            resp.setMsg("获取商品接口出错");
            resp.setToken(null);
            resp.setData(null);
            return JSON.toJSONString(resp);
        }
        resp.setCode(1);
        resp.setMsg("获取商品成功");
        resp.setToken(null);
        resp.setData(AllGoods);
        return JSON.toJSONString(resp);

    }
    @RequestMapping("getGoodsDetail")
    @ResponseBody
    public String getGoodsDetail(@RequestParam(value = "orderid",required = true)String orderid) {


        String rdata;
        try {
            List<Long> longList = new ArrayList<>();
            longList.add(Long.valueOf(orderid));
            rdata = douyinAPI.getProductDetail(longList);
        } catch (Exception exception) {
            resp.setCode(0);
            resp.setMsg("获取商品信息出错");
            resp.setToken(null);
            resp.setData(null);
            return JSON.toJSONString(resp);
        }
        resp.setCode(1);
        resp.setMsg("返回成功");
        resp.setToken(null);
        resp.setData(rdata);
        return JSON.toJSONString(resp);

    }
}
