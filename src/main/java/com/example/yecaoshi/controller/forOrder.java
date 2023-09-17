package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsResponse;
import com.doudian.open.api.buyin_doukeOrderAds.data.OrdersItem;
import com.example.yecaoshi.control.OrderControl;
import com.example.yecaoshi.mapper.HAdminMapper;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.mapper.HOrderMapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HGoods;
import com.example.yecaoshi.pojo.HOrder;
import com.example.yecaoshi.pojo.Resp;
import com.example.yecaoshi.util.CommonUtil;
import com.example.yecaoshi.util.DouyinAPI;
import com.example.yecaoshi.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("orders")
@Slf4j
@CrossOrigin


public class forOrder {
    @Autowired
    private HGoodsMapper hGoodsMapper;
    @Autowired
    private HOrderMapper hOrderMapper;

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
    @Autowired
    private HAdminMapper hAdminMapper;
    @PostMapping("orderDetailfromDouyin")
    @ResponseBody
    public String web_getOrderDetail(@RequestParam(value = "orderId")String orderid) throws ParseException {
        BuyinDoukeOrderAdsResponse buyinDoukeOrderAdsResponse = orderControl.con_getOrderDetailById(orderid);
        OrdersItem ordersItem = buyinDoukeOrderAdsResponse.getData().getOrders().get(0);
        orderControl.con_handleDouOrder(ordersItem);
        return "cc";
    }
    @PostMapping("orderlist")
    @ResponseBody
    public String ingoods(HttpServletRequest request) throws IOException, IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("params");
        long orderstatus = Long.parseLong(Req_param.get("orderstatus").toString());
        if(orderstatus==0l)
        {
            QueryWrapper<HOrder> hOrderQueryWrapper=new QueryWrapper<>();
            hOrderQueryWrapper
                    .gt("id",0);
            List<HOrder> orderList=hOrderMapper.selectList(hOrderQueryWrapper);
            resp.setCode(0);
            resp.setMsg("加载成功");
            resp.setData(orderList);
            resp.setToken(null);
            return JSON.toJSONString(resp);

        }
        Map sea_map=new HashMap();
        sea_map.put("status",orderstatus);
        List<HOrder> orderList=hOrderMapper.selectByMap(sea_map);
        resp.setCode(0);
        resp.setMsg("加载成功");
        resp.setData(orderList);
        resp.setToken(null);
        return JSON.toJSONString(resp);
    }
    @PostMapping("orderlistforuser")
    @ResponseBody
    public String orderlistforuser(HttpServletRequest request) throws IOException, IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("params");
        long orderstatus = Long.parseLong(Req_param.get("status").toString());
        long uid=Long.parseLong(Req_param.get("uid").toString());

        Map sea_map=new HashMap();
        sea_map.put("status",orderstatus);
        sea_map.put("uid",uid);
        List<HOrder> orderList=hOrderMapper.selectByMap(sea_map);
        resp.setCode(0);
        resp.setMsg("加载成功");
        resp.setData(orderList);
        resp.setToken(null);
        return JSON.toJSONString(resp);
    }
    //根据筛选条件来筛选订单
    @PostMapping("filterOrder")
    @ResponseBody
    public String filterOrder(HttpServletRequest request) throws IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("filterForm");
        QueryWrapper<HOrder> queryWrapper=new QueryWrapper<>();

        if(Req_param.get("userid")!=null && !Objects.equals(Req_param.get("userid").toString(), ""))
        {
            queryWrapper
                    .eq("uid",Long.parseLong(Req_param.get("userid").toString()));
        }
        if(Req_param.get("order_sn")!=null && !Objects.equals(Req_param.get("order_sn").toString(), ""))
        {
            queryWrapper
                    .eq("order_no",Req_param.get("order_sn").toString());
        }
        if(Req_param.get("good_sn")!=null  && !Objects.equals(Req_param.get("good_sn").toString(), ""))
        {
            queryWrapper
                    .eq("goods_id",Req_param.get("good_sn").toString());
        }
        List<HOrder> list = hOrderMapper.selectList(queryWrapper);
        return JSON.toJSONString(resp.returnSuccessWithData(list));
    }
    @PostMapping("failOrder")
    @ResponseBody
    public String failOrder(HttpServletRequest request) throws IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("params");
        QueryWrapper<HOrder> queryWrapper=new QueryWrapper<>();
        String orderno=Req_param.get("id").toString();
        System.out.println(orderno.toString());
        queryWrapper
                .eq("order_no",orderno);

        HOrder hOrder=hOrderMapper.selectOne(queryWrapper);
        hOrder.setStatus(4l);
        hOrder.setStatus(4l);
        String reasonfail=Req_param.get("failreason").toString();
        hOrder.setReasonFailure(reasonfail);
        hOrderMapper.updateById(hOrder);
        return JSON.toJSONString(resp.returnSuccess("操作成功"));



    }
    @PostMapping("courseInfo")
    @ResponseBody
    public String courseInfo(HttpServletRequest request) throws IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("param");
        //orderpaytiemm 13位时间戳
        Long count=3L;
        long today=this.getTimeToday();
        long mingtian=today+60*60*24*1000;
        System.out.println(mingtian);
        QueryWrapper<HOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.between("order_pay_time",today,mingtian);
        Long aLong = hOrderMapper.selectCount(queryWrapper);
        Map re=new HashMap<>();
        re.put("count",count-aLong);
        return JSON.toJSONString(resp.returnSuccessWithData(re));

    }
    public long getTimeToday()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayZeroTimestamp = calendar.getTimeInMillis();
        return todayZeroTimestamp;
    }
}
