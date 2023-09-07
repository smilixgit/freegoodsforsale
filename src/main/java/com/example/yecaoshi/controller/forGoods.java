package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.pojo.HGoods;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("goods")
@Slf4j

public class forGoods {
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
    public String getGoodsDetail(@RequestParam(value = "goodsid",required = true)String orderid) {


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

    @PostMapping("getDycode")
    @ResponseBody
    public String getDoukouling(HttpServletRequest request) throws IOException {
        Map requestReq = commonUtil.handleHttpServletRequestReq(request);
        String ext_info="uid_"+requestReq.get("uid").toString()+"_gid_"+requestReq.get("id").toString();
        resp.setCode(1);

        resp.setToken(null);
        resp.setData(douyinAPI.getDouKouLing(requestReq.get("goods_url").toString(),ext_info));
        resp.setMsg("转链成功");
        return JSON.toJSONString(resp);
    }
}
